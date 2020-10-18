package com.browarna.railwaycarsserving.service;

import com.browarna.railwaycarsserving.exceptions.RailwayCarsServingException;
import com.browarna.railwaycarsserving.model.RefreshToken;
import com.browarna.railwaycarsserving.repository.RefreshTokenRepository;
import com.browarna.railwaycarsserving.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private static final String TOKEN_DELETED = "Refresh token deleted";
    private static final String TOKEN_WAS_NOT_DELETED = "Refresh token was not found";

    public RefreshToken generateRefreshToken(String username) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());
        refreshToken.setUser(userRepository.findByUsername(username).get());

        return refreshTokenRepository.save(refreshToken);
    }

    boolean validateRefreshToken(String token) {
        return refreshTokenRepository.findByToken(token).isPresent();
    }

    public String deleteRefreshToken(String token) {
        if (refreshTokenRepository.findByToken(token).isPresent()) {
            refreshTokenRepository.deleteByToken(token);
            return TOKEN_DELETED;
        }
        return TOKEN_WAS_NOT_DELETED;
    }

    public String deleteRefreshTokenById(Long refreshTokenId) {
        if (refreshTokenRepository.findById(refreshTokenId).isPresent()) {
            refreshTokenRepository.deleteById(refreshTokenId);
            return TOKEN_DELETED;
        }
        return TOKEN_WAS_NOT_DELETED;
    }
}
