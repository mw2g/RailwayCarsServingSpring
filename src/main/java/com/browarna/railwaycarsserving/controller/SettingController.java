package com.browarna.railwaycarsserving.controller;

import com.browarna.railwaycarsserving.model.Setting;
import com.browarna.railwaycarsserving.service.SettingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/setting")
@AllArgsConstructor
public class SettingController {

    private final SettingService settingService;

    @GetMapping
    public ResponseEntity<List<Setting>> getAll() {
        return ResponseEntity.status(OK)
                .body(settingService.getAll());
    }

    @PostMapping("/{settingTypes}")
    public ResponseEntity<List<String>> getByType(@RequestBody List<String> settingTypes) {
        return ResponseEntity.status(OK)
                .body(settingService.getByType(settingTypes));
    }

    @PostMapping
    public ResponseEntity<Setting> create(@RequestBody Setting setting) {
        return ResponseEntity.status(OK)
                .body(settingService.create(setting));
    }

    @PutMapping
    public ResponseEntity update(@RequestBody Setting setting) {
        settingService.update(setting);
        return ResponseEntity.status(OK).build();
    }

    @DeleteMapping("/{settingType}")
    public ResponseEntity delete(@PathVariable Long settingId) {
        settingService.delete(settingId);
        return ResponseEntity.status(OK).build();
    }
}
