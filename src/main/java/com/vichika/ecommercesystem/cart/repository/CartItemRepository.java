package com.vichika.ecommercesystem.cart.repository;

import com.vichika.ecommercesystem.cart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
}
