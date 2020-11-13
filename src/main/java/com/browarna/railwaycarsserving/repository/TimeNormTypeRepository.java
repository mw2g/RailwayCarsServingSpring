package com.browarna.railwaycarsserving.repository;

import com.browarna.railwaycarsserving.model.TimeNormType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TimeNormTypeRepository extends JpaRepository<TimeNormType, Long> {
    Optional<TimeNormType> findByTypeName(String typeName);
}
