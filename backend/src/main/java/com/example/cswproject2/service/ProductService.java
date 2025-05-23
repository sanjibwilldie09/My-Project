package com.example.cswproject2.service;

import com.example.cswproject2.model.Product;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ProductService {
    private List<Product> products = new ArrayList<>();

    public ProductService() {
        products.add(new Product(1, "Drinks", "images/il_600x600.5888758057_75oe.webp", 499.99));
        products.add(new Product(2, "T-Shirt", "images/il_600x600.6262197763_dlvq.png", 299.99));
        products.add(new Product(3, "Basket", "images/il_600x600.6282843163_ot5c.png", 799.99));
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public Product getProductById(int id) {
        for (Product p : products) {
            if (p.getId() == id) return p;
        }
        return null;
    }
}
