package com.example.cswproject2.controller;

import com.example.cswproject2.model.CartItem;
import com.example.cswproject2.model.Order;
import com.example.cswproject2.model.OrderItem;
import com.example.cswproject2.model.Product;
import com.example.cswproject2.service.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class OrderController {

    private static final String ORDERS_FILE = "orders.json";
    private static int orderIdCounter = 1;

    @Autowired
    private ProductService productService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/api/order")
    public String placeOrder(@RequestBody List<CartItem> cart) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem item : cart) {
            Product p = productService.getProductById(item.getProductId());
            if (p != null) {
                orderItems.add(new OrderItem(p.getId(), p.getName(), item.getQuantity()));
            }
        }
        int orderId = getNextOrderId();
        Order order = new Order(orderId, orderItems, "Placed");

        // Save order to file
        saveOrder(order);
        return "Order placed successfully!";
    }

    @GetMapping("/api/orders")
    public List<Order> getOrders() {
        return readOrders();
    }

    // NEW: Clear all orders
    @DeleteMapping("/api/orders")
    public void clearOrders() {
        File file = new File(ORDERS_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    // --- Helper methods ---

    private synchronized int getNextOrderId() {
        // Find max id in file and increment
        List<Order> orders = readOrders();
        int maxId = 0;
        for (Order o : orders) {
            if (o.getId() > maxId) maxId = o.getId();
        }
        return maxId + 1;
    }

    private synchronized void saveOrder(Order order) {
        List<Order> orders = readOrders();
        orders.add(order);
        try (FileWriter fw = new FileWriter(ORDERS_FILE)) {
            objectMapper.writeValue(fw, orders);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized List<Order> readOrders() {
        File file = new File(ORDERS_FILE);
        if (!file.exists()) return new ArrayList<>();
        try (FileReader fr = new FileReader(file)) {
            return objectMapper.readValue(fr, new TypeReference<List<Order>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
