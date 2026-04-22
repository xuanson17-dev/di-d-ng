package com.example.hitcapp;

import java.util.ArrayList;
import java.util.List;

// Lớp quản lý giỏ hàng dùng chung (Static)
public class CartManager {
    private static List<Product> cartItems = new ArrayList<>();

    // Thêm sản phẩm vào giỏ hàng
    public static void addProduct(Product product) {
        for (Product item : cartItems) {
            if (item.getId().equals(product.getId())) {
                // Nếu đã có, tăng số lượng
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        // Nếu chưa có, set số lượng là 1 và thêm mới
        product.setQuantity(1);
        cartItems.add(product);
    }

    // Tăng số lượng
    public static void incrementQuantity(String productId) {
        for (Product item : cartItems) {
            if (item.getId().equals(productId)) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
    }

    // Giảm số lượng
    public static void decrementQuantity(String productId) {
        for (int i = 0; i < cartItems.size(); i++) {
            Product item = cartItems.get(i);
            if (item.getId().equals(productId)) {
                if (item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1);
                } else {
                    cartItems.remove(i);
                }
                return;
            }
        }
    }

    public static List<Product> getCartItems() {
        return cartItems;
    }

    // Tính tổng số lượng sản phẩm
    public static int getTotalQuantity() {
        int total = 0;
        for (Product item : cartItems) {
            total += item.getQuantity();
        }
        return total;
    }

    // Tính tổng tiền
    public static double getTotalPrice() {
        double total = 0;
        for (Product item : cartItems) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    // Xóa giỏ hàng sau khi thanh toán xong
    public static void clearCart() {
        cartItems.clear();
    }
}