package com.vichika.ecommercesystem.checkout.repository;

import com.vichika.ecommercesystem.checkout.model.Order;
import com.vichika.ecommercesystem.checkout.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    List<OrderItem> findByOrder(Order order);

    @Query("""
       SELECT COALESCE(SUM(oi.quantity * oi.product.costPrice),0)
       FROM OrderItem oi
       WHERE oi.order.status = 'PAID'
       AND DATE(oi.order.createdAt)
       BETWEEN :startDate AND :endDate
       """)
    BigDecimal getExpense(LocalDate startDate, LocalDate endDate);

    @Query("""
       SELECT COALESCE(SUM(oi.quantity * oi.product.costPrice),0)
       FROM OrderItem oi
       WHERE oi.order.status = 'PAID'
       """)
    BigDecimal getTotalExpense();
}
