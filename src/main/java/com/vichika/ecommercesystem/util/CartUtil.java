package com.vichika.ecommercesystem.util;

import com.vichika.ecommercesystem.cart.CartMapper;
import com.vichika.ecommercesystem.cart.dto.response.CartItemResponse;
import com.vichika.ecommercesystem.cart.dto.response.CartResponse;
import com.vichika.ecommercesystem.cart.model.Cart;
import com.vichika.ecommercesystem.cart.model.CartItem;
import com.vichika.ecommercesystem.cart.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Component
public class CartUtil {

    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;

    public CartResponse buildCartResponse(Cart cart){

        List<CartItem> items = cartItemRepository.findByCart(cart);

        List<CartItemResponse> itemResponses = items.stream()
                .map(cartMapper::toCartItemResponse)
                .toList();

        BigDecimal total = itemResponses.stream()
                .map(CartItemResponse::finalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new CartResponse(
                cart.getId(),
                itemResponses,
                total
        );
    }
}
