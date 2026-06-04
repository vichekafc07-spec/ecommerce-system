package com.vichika.ecommercesystem.admin.service;

import com.vichika.ecommercesystem.admin.dto.response.DashboardResponse;
import com.vichika.ecommercesystem.admin.dto.response.MonthlySalesResponse;

import java.util.List;

public interface DashboardService {
    DashboardResponse getDashboard();
    List<MonthlySalesResponse> getMonthlySales(Integer year);
}
