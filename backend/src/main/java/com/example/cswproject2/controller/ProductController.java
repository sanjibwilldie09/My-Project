package com.example.cswproject2.controller;

import com.example.cswproject2.model.Product;
import com.example.cswproject2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/api/products")
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/api/products/{id}")
    public Product getProduct(@PathVariable int id) {
        return productService.getProductById(id);
    }
}
