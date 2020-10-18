package com.browarna.railwaycarsserving.controller;

import com.browarna.railwaycarsserving.model.TariffType;
import com.browarna.railwaycarsserving.model.TariffType;
import com.browarna.railwaycarsserving.service.TariffTypeService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/tariff-type")
@AllArgsConstructor
public class TariffTypeController {

    private final TariffTypeService tariffTypeService;

    @GetMapping
    public ResponseEntity<List<TariffType>> getAllTariffType() {
        return ResponseEntity.status(OK)
                .body(tariffTypeService.getAllTariffTypes());
    }

    @GetMapping("/{typeId}")
    public ResponseEntity<TariffType> getTariffTypeById(@PathVariable Long typeId) {
        return ResponseEntity.status(OK)
                .body(tariffTypeService.getTariffTypeById(typeId));
    }

    @PostMapping
    public ResponseEntity<TariffType> createTariffType(@RequestBody TariffType tariffType) {
        return ResponseEntity.status(OK)
                .body(tariffTypeService.createTariffType(tariffType));
    }

    @PutMapping
    public String updateTariffType(@RequestBody TariffType tariffType) {
        tariffTypeService.updateTariffType(tariffType);
        return new JSONObject().put("message", "Изменения сохранены").toString();
    }

    @DeleteMapping("/{typeId}")
    public String deleteTariffType(@PathVariable Long typeId) {
        tariffTypeService.deleteTariffType(typeId);
        return new JSONObject().put("message", "Вид тарифа удален").toString();
    }
}
