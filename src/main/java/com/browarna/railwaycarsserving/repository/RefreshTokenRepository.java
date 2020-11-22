package com.browarna.railwaycarsserving.repository;

import com.browarna.railwaycarsserving.model.User;
import com.browarna.railwaycarsserving.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findAllByUser(User user);
    void deleteByToken(String token);
}
