package com.vichika.ecommercesystem.checkout.repository;

import com.vichika.ecommercesystem.admin.projection.TopProductProjection;
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

    @Query(value = """
        SELECT
            p.id AS productId,
            p.name AS productName,
            SUM(oi.quantity) AS soldQuantity
        FROM order_items oi
        JOIN products p
            ON oi.product_id = p.id
        JOIN orders o
            ON oi.order_id = o.id
        WHERE o.status = 'PAID'
        GROUP BY p.id, p.name
        ORDER BY soldQuantity DESC
        LIMIT :limit
        """, nativeQuery = true)
    List<TopProductProjection> getTopSellingProducts(Integer limit);
}
