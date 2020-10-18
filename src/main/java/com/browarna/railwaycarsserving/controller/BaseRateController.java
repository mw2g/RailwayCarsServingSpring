package com.browarna.railwaycarsserving.controller;

import com.browarna.railwaycarsserving.model.BaseRate;
import com.browarna.railwaycarsserving.service.BaseRateService;
import com.browarna.railwaycarsserving.service.BaseRateService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/base-rate")
@AllArgsConstructor
public class BaseRateController {

    private final BaseRateService baseRateService;

    @GetMapping
    public ResponseEntity<List<BaseRate>> getAll() {
        return ResponseEntity.status(OK)
                .body(baseRateService.getAll());
    }

    @GetMapping("/{rateId}")
    public ResponseEntity<BaseRate> getById(@PathVariable Long rateId) {
        return ResponseEntity.status(OK)
                .body(baseRateService.getBaseRateById(rateId));
    }

    @PostMapping
    public ResponseEntity<BaseRate> create(@RequestBody BaseRate wagonGroup) {
        return ResponseEntity.status(OK)
                .body(baseRateService.create(wagonGroup));
    }

    @PutMapping
    public ResponseEntity update(@RequestBody BaseRate wagonGroup) {
        baseRateService.update(wagonGroup);
//        return new JSONObject().put("message", "Изменения сохранены").toString();
        return ResponseEntity.status(OK).build();
    }

    @DeleteMapping("/{rateId}")
    public ResponseEntity delete(@PathVariable Long rateId) {
        baseRateService.delete(rateId);
        return ResponseEntity.status(OK).build();
//        return new JSONObject().put("message", "Вид тарифа удален").toString();
    }
}
