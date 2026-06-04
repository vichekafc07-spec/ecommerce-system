package com.vichika.ecommercesystem.admin.service.impl;

import com.vichika.ecommercesystem.admin.dto.response.DashboardResponse;
import com.vichika.ecommercesystem.admin.dto.response.MonthlySalesResponse;
import com.vichika.ecommercesystem.admin.dto.response.TopProductResponse;
import com.vichika.ecommercesystem.admin.service.DashboardService;
import com.vichika.ecommercesystem.category.CategoryRepository;
import com.vichika.ecommercesystem.checkout.repository.OrderItemRepository;
import com.vichika.ecommercesystem.checkout.repository.OrderRepository;
import com.vichika.ecommercesystem.product.repository.ProductRepository;
import com.vichika.ecommercesystem.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Month;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    @Override
    public DashboardResponse getDashboard() {

        BigDecimal revenue = orderRepository.getTotalRevenue();
        BigDecimal expense = orderItemRepository.getTotalExpense();
        BigDecimal profit = revenue.subtract(expense);

        Long totalOrders = orderRepository.getTotalOrders();
        Long totalUsers = userRepository.count();
        Long totalProducts = productRepository.count();
        Long totalCategories = categoryRepository.count();

        return new DashboardResponse(
                revenue,
                expense,
                profit,
                totalOrders,
                totalUsers,
                totalProducts,
                totalCategories
        );
    }

    @Override
    public List<MonthlySalesResponse> getMonthlySales(Integer year) {
        return orderRepository.getMonthlySales(year)
                .stream()
                .map(sale -> new MonthlySalesResponse(
                        Month.of(sale.getMonth()).name(),
                        sale.getRevenue()))
                .toList();
    }

    @Override
    public List<TopProductResponse> getTopSellingProducts(Integer limit) {

        return orderItemRepository.getTopSellingProducts(limit)
                .stream()
                .map(product ->
                        new TopProductResponse(
                                product.getProductId(),
                                product.getProductName(),
                                product.getSoldQuantity())
                ).toList();
    }
}
