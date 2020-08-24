package com.browarna.railwaycarsserving.controller;

import com.browarna.railwaycarsserving.dto.AuthenticationResponse;
import com.browarna.railwaycarsserving.dto.LoginRequest;
import com.browarna.railwaycarsserving.dto.RefreshTokenRequest;
import com.browarna.railwaycarsserving.service.AuthService;
import com.browarna.railwaycarsserving.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        String message = refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(OK).body(message);
    }

    @DeleteMapping("/{refreshTokenId}")
    public String deleteRefreshToken(@PathVariable Long refreshTokenId) {
        String message = refreshTokenService.deleteRefreshTokenById(refreshTokenId);
        return new JSONObject().put("message", message).toString();
    }
}
