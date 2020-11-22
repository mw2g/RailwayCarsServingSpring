package com.browarna.railwaycarsserving.repository;

import com.browarna.railwaycarsserving.model.CargoOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoOperationRepository extends JpaRepository<CargoOperation, Long> {
    CargoOperation findByOperationName(String operationName);
}
