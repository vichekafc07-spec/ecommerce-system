package com.vichika.ecommercesystem.report;

import com.vichika.ecommercesystem.common.APIResponse;
import com.vichika.ecommercesystem.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/admin/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/financial")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<APIResponse<FinancialReportResponse>> report(@RequestParam LocalDate startDate,
                                                                       @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(APIResponse.ok(reportService.getFinancialReport(startDate, endDate)));
    }
}
