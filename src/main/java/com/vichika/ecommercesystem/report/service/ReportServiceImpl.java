package com.vichika.ecommercesystem.report.service;

import com.vichika.ecommercesystem.checkout.repository.OrderItemRepository;
import com.vichika.ecommercesystem.checkout.repository.OrderRepository;
import com.vichika.ecommercesystem.report.FinancialReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public FinancialReportResponse getFinancialReport(LocalDate startDate, LocalDate endDate) {

        var revenue = orderRepository.getRevenue(startDate, endDate);
        var expense = orderItemRepository.getExpense(startDate, endDate);
        var profit = revenue.subtract(expense);
        Long totalOrders = orderRepository.getTotalOrders(startDate, endDate);

        return new FinancialReportResponse(revenue, expense, profit, totalOrders);
    }
}
