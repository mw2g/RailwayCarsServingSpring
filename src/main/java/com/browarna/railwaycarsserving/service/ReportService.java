package com.browarna.railwaycarsserving.service;

import com.browarna.railwaycarsserving.dto.StaticReportRowDto;
import com.browarna.railwaycarsserving.repository.ReportRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ReportService {
    private final ReportRepository reportRepository;

    public List<StaticReportRowDto> getStaticReport(Date afterDate, Date beforeDate) {

        List<StaticReportRowDto> staticReport = reportRepository.staticReport(afterDate, beforeDate);
        return staticReport;
    }

    public List<StaticReportRowDto> getReport() {

        List<StaticReportRowDto> staticReport = reportRepository.report();
        return staticReport;
    }
}
