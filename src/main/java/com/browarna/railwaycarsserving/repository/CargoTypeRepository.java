package com.browarna.railwaycarsserving.repository;

import com.browarna.railwaycarsserving.model.CargoType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoTypeRepository extends JpaRepository<CargoType, Long> {
    CargoType findByTypeName(String typeName);
}
