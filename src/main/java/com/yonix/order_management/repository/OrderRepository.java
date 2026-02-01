package com.yonix.order_management.repository;

import com.yonix.order_management.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Query("""
    SELECT DISTINCT o
    FROM Order o
    JOIN FETCH o.items
    JOIN FETCH o.user
""")
    List<Order> findAllOrders();
}
