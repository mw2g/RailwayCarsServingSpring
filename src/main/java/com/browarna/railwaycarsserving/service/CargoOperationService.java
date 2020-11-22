package com.browarna.railwaycarsserving.service;

import com.browarna.railwaycarsserving.model.CargoOperation;
import com.browarna.railwaycarsserving.repository.CargoOperationRepository;
import com.browarna.railwaycarsserving.exceptions.RailwayCarsServingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class CargoOperationService {
    private final CargoOperationRepository cargoOperationRepository;

    public List<CargoOperation> getAll() {
        return cargoOperationRepository.findAll();
    }

    public CargoOperation getById(Long operationId) {
        CargoOperation operation = cargoOperationRepository.findById(operationId)
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find wagon operation by operationId"));
        return operation;
    }

    public CargoOperation create(CargoOperation cargoOperation) {
        CargoOperation operation = new CargoOperation();
        operation.setOperationName(cargoOperation.getOperationName());
        return cargoOperationRepository.save(operation);
    }

    public void update(CargoOperation cargoOperation) {
        CargoOperation operation = cargoOperationRepository.findById(cargoOperation.getOperationId())
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find wagon operation by operationId to update"));
        operation.setOperationName(cargoOperation.getOperationName());
        cargoOperationRepository.save(operation);
    }

    public void delete(Long operationId) {
        CargoOperation operation = cargoOperationRepository.findById(operationId)
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find wagon operation by operationId to delete"));
        cargoOperationRepository.delete(operation);
    }
}
