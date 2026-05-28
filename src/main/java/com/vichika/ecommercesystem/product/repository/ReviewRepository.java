package com.vichika.ecommercesystem.product.repository;

import com.vichika.ecommercesystem.auth.model.AppUser;
import com.vichika.ecommercesystem.product.model.Product;
import com.vichika.ecommercesystem.product.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    boolean existsByUserAndProduct(AppUser user, Product product);

    List<Review> findByProduct(Product product);
}
