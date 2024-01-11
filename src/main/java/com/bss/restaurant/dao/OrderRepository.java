package com.bss.restaurant.dao;

import com.bss.restaurant.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    Order findByOrderNumber(String orderNumber);
    @Query(value = "SELECT o.id, o.order_number FROM orders o", nativeQuery = true)
    List<Object[]> findAllOrderNumberAndIds();

    Page<Order> findByOrderStatus(Integer orderStatus, Pageable pageable);
}
