package com.chrisbaileydeveloper.orderservice.repository;

import com.chrisbaileydeveloper.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
