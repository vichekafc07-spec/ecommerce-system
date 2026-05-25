package com.vichika.ecommercesystem.cart;

import com.vichika.ecommercesystem.cart.dto.response.CartItemResponse;
import com.vichika.ecommercesystem.cart.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(source = "id" , target = "itemId")
    @Mapping(source = "product.id" , target = "productId")
    @Mapping(source = "product.name" , target = "productName")
    @Mapping(source = "product.price" , target = "price")
    @Mapping(target = "finalPrice" , expression = "java(calculateSubtotal(cartItem))")
    CartItemResponse toCartItemResponse(CartItem cartItem);

    default BigDecimal calculateSubtotal(CartItem cartItem) {
        return cartItem.getProduct()
                .getFinalPrice()
                .multiply(BigDecimal.valueOf(cartItem.getQuantity()));
    }
}
