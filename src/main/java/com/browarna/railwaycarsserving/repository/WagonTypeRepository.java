package com.browarna.railwaycarsserving.repository;

import com.browarna.railwaycarsserving.model.WagonType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WagonTypeRepository extends JpaRepository<WagonType, Long> {
    Optional<WagonType> findByTypeName(String typeName);
}
