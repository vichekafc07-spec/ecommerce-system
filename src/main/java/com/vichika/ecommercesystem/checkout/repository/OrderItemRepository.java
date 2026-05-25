package com.vichika.ecommercesystem.checkout.repository;

import com.vichika.ecommercesystem.checkout.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
