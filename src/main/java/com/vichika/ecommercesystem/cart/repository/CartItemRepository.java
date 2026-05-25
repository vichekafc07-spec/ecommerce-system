package com.vichika.ecommercesystem.cart.repository;

import com.vichika.ecommercesystem.cart.model.Cart;
import com.vichika.ecommercesystem.cart.model.CartItem;
import com.vichika.ecommercesystem.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

    List<CartItem> findByCart(Cart cart);

    Optional<CartItem> findByIdAndCart(Long id, Cart cart);
}
