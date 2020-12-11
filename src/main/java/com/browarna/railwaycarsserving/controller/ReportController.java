package com.browarna.railwaycarsserving.controller;

import com.browarna.railwaycarsserving.dto.GeneralSetReportRowDto;
import com.browarna.railwaycarsserving.dto.PeriodCustomerOperationDto;
import com.browarna.railwaycarsserving.dto.StaticReportRowDto;
import com.browarna.railwaycarsserving.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/report")
@AllArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/static")
    public ResponseEntity<List<StaticReportRowDto>> getStaticReport(
            @RequestBody PeriodCustomerOperationDto body) {
        return ResponseEntity.status(OK).body(reportService.getStaticReport(
                body.getAfterDate(), body.getBeforeDate(), body.getCustomer()));
    }

    @PostMapping("/general-set")
    public ResponseEntity<List<GeneralSetReportRowDto>> getGeneralSetReport(
            @RequestBody PeriodCustomerOperationDto body) {
        return ResponseEntity.status(OK).body(reportService.getGeneralSetReport(
                body.getAfterDate(), body.getBeforeDate(), body.getOperation(), body.getCustomer()));
    }

}
