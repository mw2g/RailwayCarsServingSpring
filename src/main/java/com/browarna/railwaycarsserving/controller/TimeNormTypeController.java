package com.browarna.railwaycarsserving.controller;

import com.browarna.railwaycarsserving.model.TimeNormType;
import com.browarna.railwaycarsserving.service.TimeNormTypeService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/time-norm-type")
@AllArgsConstructor
public class TimeNormTypeController {

    private final TimeNormTypeService timeNormTypeService;

    @GetMapping
    public ResponseEntity<List<TimeNormType>> getAllTimeNormType() {
        return ResponseEntity.status(OK)
                .body(timeNormTypeService.getAllTimeNormTypes());
    }

    @GetMapping("/{typeId}")
    public ResponseEntity<TimeNormType> getTimeNormTypeById(@PathVariable Long typeId) {
        return ResponseEntity.status(OK)
                .body(timeNormTypeService.getTimeNormTypeById(typeId));
    }

    @PostMapping
    public ResponseEntity<TimeNormType> createTimeNormType(@RequestBody TimeNormType timeNormType) {
        return ResponseEntity.status(OK)
                .body(timeNormTypeService.createTimeNormType(timeNormType));
    }

    @PutMapping
    public String updateTimeNormType(@RequestBody TimeNormType timeNormType) {
        timeNormTypeService.updateTimeNormType(timeNormType);
        return new JSONObject().put("message", "Изменения сохранены").toString();
    }

    @DeleteMapping("/{typeId}")
    public String deleteTimeNormType(@PathVariable Long typeId) {
        timeNormTypeService.deleteTimeNormType(typeId);
        return new JSONObject().put("message", "Вид тарифа удален").toString();
    }
}
