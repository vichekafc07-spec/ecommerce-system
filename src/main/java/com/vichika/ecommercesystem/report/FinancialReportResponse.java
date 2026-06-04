package com.vichika.ecommercesystem.report;

import java.math.BigDecimal;

public record FinancialReportResponse(
        BigDecimal revenue,
        BigDecimal expense,
        BigDecimal profit,
        Long totalOrders
) {
}
