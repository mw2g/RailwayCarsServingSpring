package com.browarna.railwaycarsserving.controller;

import com.browarna.railwaycarsserving.model.IndexToBaseRate;
import com.browarna.railwaycarsserving.service.IndexToBaseRateService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/index-to-base-rate")
@AllArgsConstructor
public class IndexToBaseRateController {

    private final IndexToBaseRateService indexToBaseRateService;

    @GetMapping
    public ResponseEntity<List<IndexToBaseRate>> getAll() {
        return ResponseEntity.status(OK)
                .body(indexToBaseRateService.getAll());
    }

    @GetMapping("/{indexId}")
    public ResponseEntity<IndexToBaseRate> getById(@PathVariable Long indexId) {
        return ResponseEntity.status(OK)
                .body(indexToBaseRateService.getById(indexId));
    }

    @PostMapping
    public ResponseEntity<IndexToBaseRate> create(@RequestBody IndexToBaseRate indexToBaseRate) {
        return ResponseEntity.status(OK)
                .body(indexToBaseRateService.create(indexToBaseRate));
    }

    @PutMapping
    public ResponseEntity update(@RequestBody IndexToBaseRate indexToBaseRate) {
        indexToBaseRateService.update(indexToBaseRate);
        return ResponseEntity.status(OK).build();
    }

    @DeleteMapping("/{indexId}")
    public ResponseEntity delete(@PathVariable Long indexId) {
        indexToBaseRateService.delete(indexId);
        return ResponseEntity.status(OK).build();
    }
}
