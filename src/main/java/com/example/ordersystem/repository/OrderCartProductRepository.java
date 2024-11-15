package com.example.ordersystem.repository;

import com.example.ordersystem.model.OrderCartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderCartProductRepository extends JpaRepository<OrderCartProduct, Long> {
}
