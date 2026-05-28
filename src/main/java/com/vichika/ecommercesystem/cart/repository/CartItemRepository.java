package com.vichika.ecommercesystem.cart.repository;

import com.vichika.ecommercesystem.cart.model.Cart;
import com.vichika.ecommercesystem.cart.model.CartItem;
import com.vichika.ecommercesystem.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

    List<CartItem> findByCart(Cart cart);

    Optional<CartItem> findByIdAndCart(Long id, Cart cart);

    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = :cartId")
    void deleteByCartId(@Param("cartId") Long cartId);

}
