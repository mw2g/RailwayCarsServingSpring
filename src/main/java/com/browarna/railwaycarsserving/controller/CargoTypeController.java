package com.browarna.railwaycarsserving.controller;

import com.browarna.railwaycarsserving.model.CargoType;
import com.browarna.railwaycarsserving.service.CargoTypeService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/cargo-type")
@AllArgsConstructor
public class CargoTypeController {

    private final CargoTypeService cargoTypeService;

    @GetMapping
    public ResponseEntity<List<CargoType>> getAllCargoTypes() {
        return ResponseEntity.status(OK)
                .body(cargoTypeService.getAllCargoTypes());
    }

    @GetMapping("/{typeId}")
    public ResponseEntity<CargoType> getUserById(@PathVariable Long typeId) {
        return ResponseEntity.status(OK)
                .body(cargoTypeService.getCargoTypeById(typeId));
    }

    @PostMapping
    public ResponseEntity<CargoType> createUser(@RequestBody CargoType cargoType) {
        return ResponseEntity.status(OK)
                .body(cargoTypeService.createCargoType(cargoType));
    }

    @PutMapping
    public String updateUser(@RequestBody CargoType cargoType) {
        cargoTypeService.updateCargoType(cargoType);
        return new JSONObject().put("message", "Изменения сохранены").toString();
    }

    @DeleteMapping("/{typeId}")
    public String deleteUser(@PathVariable Long typeId) {
        cargoTypeService.deleteCargoType(typeId);
        return new JSONObject().put("message", "Пользователь удален").toString();
    }
}
