package com.example.ordersystem.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class OrderCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double totalPrice = 0.0;

    @OneToMany(mappedBy = "orderCart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference  // Menandakan bagian yang akan diserialisasi
    private List<OrderCartProduct> orderCartProducts = new ArrayList<>();

    // Methods for adding/removing products
    public void addOrderCartProduct(OrderCartProduct orderCartProduct) {
        orderCartProducts.add(orderCartProduct);
        orderCartProduct.setOrderCart(this);
        totalPrice += orderCartProduct.getProduct().getPrice();
    }

    public void removeOrderCartProduct(OrderCartProduct orderCartProduct) {
        orderCartProducts.remove(orderCartProduct);
        totalPrice -= orderCartProduct.getProduct().getPrice();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderCartProduct> getOrderCartProducts() {
        return orderCartProducts;
    }

    public void setOrderCartProducts(List<OrderCartProduct> orderCartProducts) {
        this.orderCartProducts = orderCartProducts;
    }
}
