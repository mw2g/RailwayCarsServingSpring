package com.browarna.railwaycarsserving.repository;

import com.browarna.railwaycarsserving.model.TariffType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TariffTypeRepository extends JpaRepository<TariffType, Long> {
    Optional<TariffType> findByTypeName(String typeName);
}
