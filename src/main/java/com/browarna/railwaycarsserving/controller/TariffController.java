package com.browarna.railwaycarsserving.controller;

import com.browarna.railwaycarsserving.model.Tariff;
import com.browarna.railwaycarsserving.service.TariffService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/tariff")
@AllArgsConstructor
public class TariffController {

    private final TariffService tariffService;

    @GetMapping
    public ResponseEntity<List<Tariff>> getAllTariff() {
        return ResponseEntity.status(OK)
                .body(tariffService.getAllTariff());
    }

    @GetMapping("/{tariffId}")
    public ResponseEntity<Tariff> getTariffById(@PathVariable Long tariffId) {
        return ResponseEntity.status(OK)
                .body(tariffService.getTariffById(tariffId));
    }

    @PostMapping
    public ResponseEntity<Tariff> createTariff(@RequestBody Tariff tariff) {
        return ResponseEntity.status(OK)
                .body(tariffService.createTariff(tariff));
    }

    @PutMapping
    public String updateTariff(@RequestBody Tariff tariff) {
        tariffService.updateTariff(tariff);
        return new JSONObject().put("message", "Изменения сохранены").toString();
    }

    @DeleteMapping("/{tariffId}")
    public String deleteTariff(@PathVariable Long tariffId) {
        tariffService.deleteTariff(tariffId);
        return new JSONObject().put("message", "Вид тарифа удален").toString();
    }
}
