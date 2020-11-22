package com.browarna.railwaycarsserving.controller;

import com.browarna.railwaycarsserving.dto.StatementRateResponse;
import com.browarna.railwaycarsserving.dto.StatementDto;
import com.browarna.railwaycarsserving.dto.StatementWithRateResponse;
import com.browarna.railwaycarsserving.service.StatementService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/statement")
@AllArgsConstructor
public class StatementController {

    private final StatementService statementService;

    @GetMapping("/{afterDate}/{beforeDate}")
    public ResponseEntity<List<StatementDto>> getAllStatements(
            @PathVariable Date afterDate, @PathVariable Date beforeDate) {
        return ResponseEntity.status(OK)
                .body(statementService.getAllStatements(afterDate, beforeDate));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatementWithRateResponse> getStatementById(@PathVariable Long id) {
        return ResponseEntity.status(OK)
                .body(statementService.getStatementById(id));
    }

    @GetMapping("/rate/{id}")
    public ResponseEntity<StatementRateResponse> getStatementRate(@PathVariable Long id) {
        return ResponseEntity.status(OK)
                .body(statementService.getRate(id));
    }

    @PostMapping
    public ResponseEntity<StatementDto> createStatement(@RequestBody StatementDto statement) {
    return ResponseEntity.status(OK)
                .body(statementService.createStatement(statement));
    }

    @PutMapping
    public ResponseEntity<StatementDto> updateStatement(@RequestBody StatementDto statement) {
        return ResponseEntity.status(OK)
                .body(statementService.updateStatement(statement));
    }

    @DeleteMapping("/{id}")
    public String deleteStatement(@PathVariable Long id) {
        statementService.deleteStatement(id);
        return new JSONObject().put("message", "Памятка подачи удалена").toString();
    }
}
