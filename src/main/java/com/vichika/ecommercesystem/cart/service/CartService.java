package com.vichika.ecommercesystem.cart.service;

import com.vichika.ecommercesystem.cart.dto.request.AddToCartRequest;
import com.vichika.ecommercesystem.cart.dto.request.UpdateCartItemRequest;
import com.vichika.ecommercesystem.cart.dto.response.CartResponse;

public interface CartService {
    CartResponse addToCart(AddToCartRequest request);

    CartResponse getUserCart();

    CartResponse updateQuantity(Long itemId, UpdateCartItemRequest request);

    void removeItem(Long itemId);

    void clearCart();

    void deleteCart(Long cartId);
}
