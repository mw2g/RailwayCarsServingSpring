package com.browarna.railwaycarsserving.controller;

import com.browarna.railwaycarsserving.dto.ControllerStatementDto;
import com.browarna.railwaycarsserving.model.ControllerStatement;
import com.browarna.railwaycarsserving.service.ControllerStatementService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/controller-statement")
@AllArgsConstructor
public class ControllerStatementController {

    private final ControllerStatementService controllerStatementService;

    @GetMapping
    public ResponseEntity<List<ControllerStatementDto>> getAllControllerStatements() {
        return ResponseEntity.status(OK)
                .body(controllerStatementService.getAllControllerStatements());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ControllerStatementDto> getControllerStatementById(@PathVariable Long id) {
        return ResponseEntity.status(OK)
                .body(controllerStatementService.getControllerStatementById(id));
    }

    @PostMapping
    public ResponseEntity<ControllerStatementDto> createControllerStatement(@RequestBody ControllerStatementDto controllerStatement) {
    return ResponseEntity.status(OK)
                .body(controllerStatementService.createControllerStatement(controllerStatement));
    }

    @PutMapping
    public ResponseEntity<ControllerStatementDto> updateControllerStatement(@RequestBody ControllerStatementDto controllerStatement) {
        return ResponseEntity.status(OK)
                .body(controllerStatementService.updateControllerStatement(controllerStatement));
    }

    @DeleteMapping("/{id}")
    public String deleteControllerStatement(@PathVariable Long id) {
        controllerStatementService.deleteControllerStatement(id);
        return new JSONObject().put("message", "Памятка подачи удалена").toString();
    }
}
