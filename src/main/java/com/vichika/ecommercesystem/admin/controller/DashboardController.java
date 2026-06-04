package com.vichika.ecommercesystem.admin.controller;

import com.vichika.ecommercesystem.admin.dto.response.DashboardResponse;
import com.vichika.ecommercesystem.admin.dto.response.MonthlySalesResponse;
import com.vichika.ecommercesystem.admin.service.DashboardService;
import com.vichika.ecommercesystem.common.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<APIResponse<DashboardResponse>> getDashboard() {
        return ResponseEntity.ok(APIResponse.ok(dashboardService.getDashboard()));
    }

    @GetMapping("/monthly-sales")
    public ResponseEntity<APIResponse<List<MonthlySalesResponse>>> getMonthlySales(@RequestParam Integer year) {

        return ResponseEntity.ok(APIResponse.ok(dashboardService.getMonthlySales(year)));
    }
}
