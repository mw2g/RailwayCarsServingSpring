package com.browarna.railwaycarsserving.repository;

import com.browarna.railwaycarsserving.model.CargoOperation;
import com.browarna.railwaycarsserving.model.Wagon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoOperationRepository extends JpaRepository<CargoOperation, Long> {
    CargoOperation findByOperation(String operation);
}
