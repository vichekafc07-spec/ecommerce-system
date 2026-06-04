package com.vichika.ecommercesystem.report.service;

import com.vichika.ecommercesystem.report.FinancialReportResponse;
import java.time.LocalDate;

public interface ReportService {
    FinancialReportResponse getFinancialReport(LocalDate startDate, LocalDate endDate);
}
