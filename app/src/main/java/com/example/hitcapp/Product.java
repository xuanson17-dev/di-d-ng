package com.example.hitcapp;

import java.io.Serializable;

public class Product implements Serializable {
    private String id;
    private String name;
    private double price;
    private int imageResource;
    private String description;
    private String category; // Thêm phân loại
    private int quantity;
    private boolean isFavorite; // Thêm trạng thái yêu thích

    public Product(String id, String name, double price, int imageResource, String description, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageResource = imageResource;
        this.description = description;
        this.category = category;
        this.quantity = 1;
        this.isFavorite = false;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getImageResource() { return imageResource; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }

    public String getFormattedPrice() {
        return String.format("%,.0f VNĐ", price);
    }
}