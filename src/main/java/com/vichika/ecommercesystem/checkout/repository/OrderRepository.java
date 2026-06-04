package com.vichika.ecommercesystem.checkout.repository;

import com.vichika.ecommercesystem.admin.projection.MonthlySalesProjection;
import com.vichika.ecommercesystem.auth.model.AppUser;
import com.vichika.ecommercesystem.checkout.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUserOrderByCreatedAtDesc(AppUser user);

    Optional<Order> findByIdAndUser(Long id, AppUser user);

    @Query("""
       SELECT COALESCE(SUM(o.totalAmount),0)
       FROM Order o
       WHERE o.status = 'PAID'
       AND DATE(o.createdAt)
       BETWEEN :startDate AND :endDate
       """)
    BigDecimal getRevenue(LocalDate startDate, LocalDate endDate);

    @Query("""
       SELECT COUNT(o)
       FROM Order o
       WHERE o.status = 'PAID'
       AND DATE(o.createdAt)
       BETWEEN :startDate AND :endDate
       """)
    Long getTotalOrders(LocalDate startDate, LocalDate endDate);

    @Query("""
       SELECT COALESCE(SUM(o.totalAmount),0)
       FROM Order o
       WHERE o.status = 'PAID'
       """)
    BigDecimal getTotalRevenue();

    @Query("""
       SELECT COUNT(o)
       FROM Order o
       """)
    Long getTotalOrders();

    @Query(value = """
        SELECT
            EXTRACT(MONTH FROM o.created_at) AS month,
            SUM(o.total_amount) AS revenue
        FROM orders o
        WHERE o.status = 'PAID'
        AND EXTRACT(YEAR FROM o.created_at) = :year
        GROUP BY EXTRACT(MONTH FROM o.created_at)
        ORDER BY EXTRACT(MONTH FROM o.created_at)
        """, nativeQuery = true)
    List<MonthlySalesProjection> getMonthlySales(Integer year);
}
