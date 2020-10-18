package com.browarna.railwaycarsserving.controller;

import com.browarna.railwaycarsserving.model.WagonType;
import com.browarna.railwaycarsserving.service.WagonTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/wagon-type")
@AllArgsConstructor
public class WagonTypeController {

    private final WagonTypeService wagonTypeService;

    @GetMapping
    public ResponseEntity<List<WagonType>> getAll() {
        return ResponseEntity.status(OK)
                .body(wagonTypeService.getAll());
    }

    @GetMapping("/{typeId}")
    public ResponseEntity<WagonType> getById(@PathVariable Long typeId) {
        return ResponseEntity.status(OK)
                .body(wagonTypeService.getById(typeId));
    }

    @PostMapping
    public ResponseEntity<WagonType> create(@RequestBody WagonType wagonType) {
        return ResponseEntity.status(OK)
                .body(wagonTypeService.create(wagonType));
    }

    @PutMapping
    public ResponseEntity update(@RequestBody WagonType wagonType) {
        wagonTypeService.update(wagonType);
        return ResponseEntity.status(OK).build();
    }

    @DeleteMapping("/{typeId}")
    public ResponseEntity delete(@PathVariable Long typeId) {
        wagonTypeService.delete(typeId);
        return ResponseEntity.status(OK).build();
    }
}
