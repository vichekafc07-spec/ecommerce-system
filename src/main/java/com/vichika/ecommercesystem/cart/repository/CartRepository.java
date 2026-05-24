package com.vichika.ecommercesystem.cart.repository;

import com.vichika.ecommercesystem.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
