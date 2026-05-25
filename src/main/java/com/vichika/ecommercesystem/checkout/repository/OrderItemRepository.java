package com.vichika.ecommercesystem.checkout.repository;

import com.vichika.ecommercesystem.checkout.model.Order;
import com.vichika.ecommercesystem.checkout.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    List<OrderItem> findByOrder(Order order);
}
