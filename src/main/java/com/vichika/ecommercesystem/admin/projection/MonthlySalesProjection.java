package com.vichika.ecommercesystem.admin.projection;

import java.math.BigDecimal;

public interface MonthlySalesProjection {
    Integer getMonth();
    BigDecimal getRevenue();
}
