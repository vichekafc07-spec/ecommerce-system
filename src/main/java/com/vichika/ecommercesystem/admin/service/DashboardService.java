package com.vichika.ecommercesystem.admin.service;

import com.vichika.ecommercesystem.admin.dto.response.DashboardResponse;
import com.vichika.ecommercesystem.admin.dto.response.MonthlySalesResponse;
import com.vichika.ecommercesystem.admin.dto.response.TopProductResponse;

import java.util.List;

public interface DashboardService {
    DashboardResponse getDashboard();

    List<MonthlySalesResponse> getMonthlySales(Integer year);

    List<TopProductResponse> getTopSellingProducts(Integer limit);
}
