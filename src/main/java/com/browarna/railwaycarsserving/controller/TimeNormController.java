package com.browarna.railwaycarsserving.controller;

import com.browarna.railwaycarsserving.model.TimeNorm;
import com.browarna.railwaycarsserving.service.TimeNormService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/time-norm")
@AllArgsConstructor
public class TimeNormController {

    private final TimeNormService timeNormService;

    @GetMapping
    public ResponseEntity<List<TimeNorm>> getAllTimeNorm() {
        return ResponseEntity.status(OK)
                .body(timeNormService.getAllTimeNorm());
    }

    @GetMapping("/{normId}")
    public ResponseEntity<TimeNorm> getTimeNormById(@PathVariable Long normId) {
        return ResponseEntity.status(OK)
                .body(timeNormService.getTimeNormById(normId));
    }

    @PostMapping
    public ResponseEntity<TimeNorm> createTimeNorm(@RequestBody TimeNorm timeNorm) {
        return ResponseEntity.status(OK)
                .body(timeNormService.createTimeNorm(timeNorm));
    }

    @PutMapping
    public void updateTimeNorm(@RequestBody TimeNorm timeNorm) {
        timeNormService.updateTimeNorm(timeNorm);
//        return new JSONObject().put("message", "Изменения сохранены").toString();
    }

    @DeleteMapping("/{normId}")
    public void deleteTimeNorm(@PathVariable Long normId) {
        timeNormService.deleteTimeNorm(normId);
//        return new JSONObject().put("message", "Норматив времени удален").toString();
    }
}
