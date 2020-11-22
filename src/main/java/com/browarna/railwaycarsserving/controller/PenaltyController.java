package com.browarna.railwaycarsserving.controller;

import com.browarna.railwaycarsserving.service.PenaltyService;
import com.browarna.railwaycarsserving.model.Penalty;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/penalty")
@AllArgsConstructor
public class PenaltyController {

    private final PenaltyService penaltyService;

    @GetMapping
    public ResponseEntity<List<Penalty>> getAllPenalty() {
        return ResponseEntity.status(OK)
                .body(penaltyService.getAll());
    }

    @GetMapping("/{penaltyId}")
    public ResponseEntity<Penalty> getPenaltyById(@PathVariable Long penaltyId) {
        return ResponseEntity.status(OK)
                .body(penaltyService.getById(penaltyId));
    }

    @PostMapping
    public ResponseEntity<Penalty> createPenalty(@RequestBody Penalty penalty) {
        return ResponseEntity.status(OK)
                .body(penaltyService.create(penalty));
    }

    @PutMapping
    public String updatePenalty(@RequestBody Penalty penalty) {
        penaltyService.update(penalty);
        return new JSONObject().put("message", "Изменения сохранены").toString();
    }

    @DeleteMapping("/{penaltyId}")
    public String deletePenalty(@PathVariable Long penaltyId) {
        penaltyService.delete(penaltyId);
        return new JSONObject().put("message", "Вид тарифа удален").toString();
    }
}
