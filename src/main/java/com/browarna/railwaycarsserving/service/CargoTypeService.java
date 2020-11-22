package com.browarna.railwaycarsserving.service;

import com.browarna.railwaycarsserving.model.CargoType;
import com.browarna.railwaycarsserving.repository.CargoTypeRepository;
import com.browarna.railwaycarsserving.exceptions.RailwayCarsServingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class CargoTypeService {
    private final CargoTypeRepository cargoTypeRepository;

    public List<CargoType> getAllCargoTypes() {
        return cargoTypeRepository.findAll();
    }

    public CargoType getCargoTypeById(Long typeId) {
        CargoType cargoType = cargoTypeRepository.findById(typeId)
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find user by userId to update"));
        return cargoType;
    }

    public CargoType createCargoType(CargoType cargoType) {
        CargoType type = new CargoType();
        type.setTypeName(cargoType.getTypeName());
        return cargoTypeRepository.save(type);
    }

    public void updateCargoType(CargoType cargoType) {
        CargoType type = cargoTypeRepository.findById(cargoType.getTypeId())
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find user by userId to update"));
        type.setTypeName(cargoType.getTypeName());
        cargoTypeRepository.save(type);
    }

    public void deleteCargoType(Long typeId) {
        CargoType cargoType = cargoTypeRepository.findById(typeId)
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find user by userId to delete"));
        cargoTypeRepository.delete(cargoType);
    }
}
