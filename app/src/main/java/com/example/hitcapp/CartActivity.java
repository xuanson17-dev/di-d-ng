package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private TextView tvTotalItems, tvTotalPrice;
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        RecyclerView rvCart = findViewById(R.id.rvCartItems);
        tvTotalItems = findViewById(R.id.tvTotalItems);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        Button btnCheckout = findViewById(R.id.btnCheckout);
        ImageView btnBack = findViewById(R.id.btnBackCart);

        rvCart.setLayoutManager(new LinearLayoutManager(this));

        // Lấy danh sách từ CartManager
        List<Product> cartList = CartManager.getCartItems();

        adapter = new ProductAdapter(cartList, product -> {
            // Xem chi tiết nếu muốn
        }, true);

        // Lắng nghe sự thay đổi số lượng để cập nhật lại UI tổng tiền
        adapter.setOnQuantityChangeListener(() -> {
            updateSummary();
            if (CartManager.getCartItems().isEmpty()) {
                adapter.notifyDataSetChanged();
            }
        });

        rvCart.setAdapter(adapter);

        updateSummary();

        btnBack.setOnClickListener(v -> finish());

        btnCheckout.setOnClickListener(v -> {
            if (CartManager.getCartItems().isEmpty()) {
                android.widget.Toast.makeText(this, "Giỏ hàng đang trống!", android.widget.Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
            intent.putExtra("total_price", CartManager.getTotalPrice());
            startActivity(intent);
        });
    }

    private void updateSummary() {
        tvTotalItems.setText(CartManager.getTotalQuantity() + " sản phẩm");
        tvTotalPrice.setText(String.format("%,.0f VNĐ", CartManager.getTotalPrice()));
    }
}