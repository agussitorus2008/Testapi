package com.example.ordersystem.service;

import com.example.ordersystem.model.OrderCart;
import com.example.ordersystem.model.OrderCartProduct;
import com.example.ordersystem.model.Product;
import com.example.ordersystem.repository.OrderCartRepository;
import com.example.ordersystem.repository.OrderCartProductRepository;
import com.example.ordersystem.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderCartService {

    @Autowired
    private OrderCartRepository orderCartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderCartProductRepository orderCartProductRepository;

    public OrderCart getOrderCart(Long cartId) {
        return orderCartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Order cart not found"));
    }

    public OrderCart createOrderCart() {
        return orderCartRepository.save(new OrderCart());
    }

    // Mengambil semua order carts dan menghitung total price keseluruhan
    public OrderCartResponse getAllOrderCarts() {
        List<OrderCart> orderCarts = orderCartRepository.findAll();
        double totalPriceAllCarts = 0.0;

        // Hitung total price untuk setiap order cart dan total keseluruhan
        for (OrderCart orderCart : orderCarts) {
            double totalPrice = calculateTotalPrice(orderCart);
            orderCart.setTotalPrice(totalPrice);
            totalPriceAllCarts += totalPrice;
        }

        return new OrderCartResponse(orderCarts, totalPriceAllCarts);
    }

    public OrderCart addProductToCart(Long cartId, Long productId) {
        OrderCart orderCart = getOrderCart(cartId);
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));

        OrderCartProduct existingOrderCartProduct = orderCart.getOrderCartProducts().stream()
            .filter(orderCartProduct -> orderCartProduct.getProduct().getId().equals(productId))
            .findFirst()
            .orElse(null);

        if (existingOrderCartProduct != null) {
            existingOrderCartProduct.setQuantity(existingOrderCartProduct.getQuantity() + 1);
            orderCart.setTotalPrice(calculateTotalPrice(orderCart));
            orderCartProductRepository.save(existingOrderCartProduct);
            return orderCartRepository.save(orderCart);
        }

        OrderCartProduct orderCartProduct = new OrderCartProduct();
        orderCartProduct.setOrderCart(orderCart);
        orderCartProduct.setProduct(product);
        orderCartProduct.setQuantity(1);
        orderCart.addOrderCartProduct(orderCartProduct);
        orderCart.setTotalPrice(calculateTotalPrice(orderCart));

        orderCartProductRepository.save(orderCartProduct);
        return orderCartRepository.save(orderCart);
    }

    private Double calculateTotalPrice(OrderCart orderCart) {
        double totalPrice = 0.0;
        for (OrderCartProduct orderCartProduct : orderCart.getOrderCartProducts()) {
            double productPrice = orderCartProduct.getProduct().getPrice();
            int quantity = orderCartProduct.getQuantity();
            totalPrice += productPrice * quantity;
        }
        return totalPrice;
    }

    // Fungsi untuk menghitung total harga semua order cart
    public Double calculateTotalPriceAllCarts() {
        double totalPriceAllCarts = 0.0;
        List<OrderCart> orderCarts = orderCartRepository.findAll();
        for (OrderCart orderCart : orderCarts) {
            totalPriceAllCarts += orderCart.getTotalPrice();
        }
        return totalPriceAllCarts;
    }

    public OrderCart placeOrder(Long cartId) {
        OrderCart orderCart = getOrderCart(cartId);
        // Logic for saving the order can be added here (e.g., storing in an Order entity)
        return orderCart;
    }

    public static class OrderCartResponse {
        private List<OrderCart> orderCarts;
        private Double totalPriceAllCarts;

        public OrderCartResponse(List<OrderCart> orderCarts, Double totalPriceAllCarts) {
            this.orderCarts = orderCarts;
            this.totalPriceAllCarts = totalPriceAllCarts;
        }

        public List<OrderCart> getOrderCarts() {
            return orderCarts;
        }

        public void setOrderCarts(List<OrderCart> orderCarts) {
            this.orderCarts = orderCarts;
        }

        public Double getTotalPriceAllCarts() {
            return totalPriceAllCarts;
        }

        public void setTotalPriceAllCarts(Double totalPriceAllCarts) {
            this.totalPriceAllCarts = totalPriceAllCarts;
        }
    }
}
