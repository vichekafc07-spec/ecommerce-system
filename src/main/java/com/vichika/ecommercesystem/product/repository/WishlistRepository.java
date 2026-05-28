package com.vichika.ecommercesystem.product.repository;

import com.vichika.ecommercesystem.auth.model.AppUser;
import com.vichika.ecommercesystem.product.model.Product;
import com.vichika.ecommercesystem.product.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> , JpaSpecificationExecutor<Wishlist> {

    long countByUser(AppUser user);

    boolean existsByUserAndProduct(AppUser user, Product product);

    Optional<Wishlist> findByUserAndProduct(AppUser user, Product product);
}
