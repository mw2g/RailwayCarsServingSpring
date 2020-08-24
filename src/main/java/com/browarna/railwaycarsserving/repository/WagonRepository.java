package com.browarna.railwaycarsserving.repository;

import com.browarna.railwaycarsserving.model.Wagon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WagonRepository extends JpaRepository<Wagon, Long> {
    Optional<Wagon> findByWagonNumber(String wagonNumber);
}
