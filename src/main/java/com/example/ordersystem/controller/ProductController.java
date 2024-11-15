package com.example.ordersystem.controller;

import com.example.ordersystem.model.Product;
import com.example.ordersystem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts(
        @RequestParam(defaultValue = "0") int page,  
        @RequestParam(defaultValue = "10") int size   // Default ke ukuran 10 produk per halaman
    ) {
        // Mendapatkan halaman produk dari service
        Page<Product> productPage = productService.getAllProducts(page, size);
        
        // Mengembalikan hanya konten produk (tanpa metadata pagination)
        return productPage.getContent();
    }

    // API untuk mengambil produk berdasarkan ID
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    // API untuk menambahkan produk baru
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    // API untuk mengupdate data produk
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    // API untuk menghapus produk
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}

