package com.example.ordersystem.controller;

import com.example.ordersystem.model.OrderCart;
import com.example.ordersystem.service.OrderCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-carts")
public class OrderCartController {

    @Autowired
    private OrderCartService orderCartService;

    // Endpoint untuk membuat order cart baru
    @PostMapping
    public OrderCart createOrderCart() {
        return orderCartService.createOrderCart();
    }

    @GetMapping
    public OrderCartService.OrderCartResponse getAllOrderCarts() {
        return orderCartService.getAllOrderCarts();
    }

    // Endpoint untuk mendapatkan order cart by ID
    @GetMapping("/{cartId}")
    public OrderCart getOrderCart(@PathVariable Long cartId) {
        return orderCartService.getOrderCart(cartId);
    }

    // Endpoint untuk menambahkan produk ke dalam order cart
    @PostMapping("/{cartId}/products/{productId}")
    public OrderCart addProductToCart(@PathVariable Long cartId, @PathVariable Long productId) {
        return orderCartService.addProductToCart(cartId, productId);
    }

    // Endpoint untuk menempatkan order
    @PostMapping("/{cartId}/place-order")
    public OrderCart placeOrder(@PathVariable Long cartId) {
        return orderCartService.placeOrder(cartId);
    }
    
}
