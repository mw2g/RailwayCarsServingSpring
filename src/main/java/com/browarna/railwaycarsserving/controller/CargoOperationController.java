package com.browarna.railwaycarsserving.controller;

import com.browarna.railwaycarsserving.model.CargoOperation;
import com.browarna.railwaycarsserving.service.CargoOperationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/cargo-operation")
@AllArgsConstructor
public class CargoOperationController {

    private final CargoOperationService cargoOperationService;

    @GetMapping
    public ResponseEntity<List<CargoOperation>> getAll() {
        return ResponseEntity.status(OK)
                .body(cargoOperationService.getAll());
    }

    @GetMapping("/{operationId}")
    public ResponseEntity<CargoOperation> getById(@PathVariable Long operationId) {
        return ResponseEntity.status(OK)
                .body(cargoOperationService.getById(operationId));
    }

    @PostMapping
    public ResponseEntity<CargoOperation> create(@RequestBody CargoOperation cargoOperation) {
        return ResponseEntity.status(OK)
                .body(cargoOperationService.create(cargoOperation));
    }

    @PutMapping
    public ResponseEntity update(@RequestBody CargoOperation cargoOperation) {
        cargoOperationService.update(cargoOperation);
        return ResponseEntity.status(OK).build();
    }

    @DeleteMapping("/{operationId}")
    public ResponseEntity delete(@PathVariable Long operationId) {
        cargoOperationService.delete(operationId);
        return ResponseEntity.status(OK).build();
    }
}
