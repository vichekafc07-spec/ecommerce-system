package com.vichika.ecommercesystem.product.repository;

import com.vichika.ecommercesystem.product.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {
}
