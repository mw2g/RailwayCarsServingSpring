package com.browarna.railwaycarsserving.controller;

import com.browarna.railwaycarsserving.dto.StaticReportRowDto;
import com.browarna.railwaycarsserving.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/report")
@AllArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/{afterDate}/{beforeDate}")
    public ResponseEntity<List<StaticReportRowDto>> getStaticReport(
            @PathVariable Date afterDate, @PathVariable Date beforeDate) {
        return ResponseEntity.status(OK).body(reportService.getStaticReport(afterDate, beforeDate));
    }

    @GetMapping
    public ResponseEntity<List<StaticReportRowDto>> getReport() {
        return ResponseEntity.status(OK).body(reportService.getReport());
    }
}
