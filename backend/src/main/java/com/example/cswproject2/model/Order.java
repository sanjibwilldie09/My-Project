package com.example.cswproject2.model;

import java.util.List;

public class Order {
    private int id;
    private List<OrderItem> items;
    private String status;

    public Order() {}

    public Order(int id, List<OrderItem> items, String status) {
        this.id = id;
        this.items = items;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
