package com.vichika.ecommercesystem.admin.dto.response;

import java.math.BigDecimal;

public record MonthlySalesResponse(
        String month,
        BigDecimal revenue
) {
}
