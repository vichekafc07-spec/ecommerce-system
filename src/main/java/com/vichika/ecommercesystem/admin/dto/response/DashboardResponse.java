package com.vichika.ecommercesystem.admin.dto.response;

import java.math.BigDecimal;

public record DashboardResponse(

        BigDecimal revenue,

        BigDecimal expense,

        BigDecimal profit,

        Long totalOrders,

        Long totalUsers,

        Long totalProducts,

        Long totalCategories
) {
}
