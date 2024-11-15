package com.example.ordersystem.repository;

import com.example.ordersystem.model.OrderCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderCartRepository extends JpaRepository<OrderCart, Long> {
}
