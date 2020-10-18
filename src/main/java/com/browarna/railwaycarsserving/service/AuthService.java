package com.browarna.railwaycarsserving.service;

import com.browarna.railwaycarsserving.dto.AuthenticationResponse;
import com.browarna.railwaycarsserving.dto.LoginRequest;
import com.browarna.railwaycarsserving.dto.RefreshTokenRequest;
import com.browarna.railwaycarsserving.model.User;
import com.browarna.railwaycarsserving.repository.UserRepository;
import com.browarna.railwaycarsserving.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        User principal = (User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken(loginRequest.getUsername()).getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .build();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        if (refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken()) == true) {
            String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
            return AuthenticationResponse.builder()
                    .authenticationToken(token)
                    .refreshToken(refreshTokenRequest.getRefreshToken())
                    .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                    .username(refreshTokenRequest.getUsername())
                    .build();

        }
        return AuthenticationResponse.builder()
                .refreshToken("kickedOut")
                .build();
    }
}
