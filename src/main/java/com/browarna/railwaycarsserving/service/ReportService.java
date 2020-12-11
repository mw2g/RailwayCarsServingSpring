package com.browarna.railwaycarsserving.service;

import com.browarna.railwaycarsserving.dto.GeneralSetReportRowDto;
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

    public List<StaticReportRowDto> getStaticReport(Date afterDate, Date beforeDate, String customerName) {
        List<StaticReportRowDto> report;
        if (customerName.equals("")) {
            report = reportRepository.staticReport(afterDate, beforeDate);
        } else {
            report = reportRepository.staticReportForCustomer(afterDate, beforeDate, customerName);
        }
        return report;
    }

    public List<GeneralSetReportRowDto> getGeneralSetReport(Date afterDate, Date beforeDate, String excludeOperation, String customerName) {
        List<GeneralSetReportRowDto> report;
        if (customerName.equals("")) {
            report = reportRepository.generalSetReport(afterDate, beforeDate, excludeOperation);
        } else {
            report = reportRepository.generalSetReportForCustomer(afterDate, beforeDate, excludeOperation, customerName);
        }
        return report;
    }
}
