package com.vichika.ecommercesystem.product.dto.response;

import java.math.BigDecimal;

public record WishlistResponse(

        Long wishlistId,
        Long productId,
        String productName,
        BigDecimal price,
        String imageUrl
) {
}
