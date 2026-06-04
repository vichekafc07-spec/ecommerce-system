package com.vichika.ecommercesystem.admin;

import java.math.BigDecimal;

public interface MonthlySalesProjection {
    Integer getMonth();
    BigDecimal getRevenue();
}
