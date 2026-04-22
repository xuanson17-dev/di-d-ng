package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button btnLogout = findViewById(R.id.btnLogout);
        LinearLayout btnOrderHistory = findViewById(R.id.btnOrderHistoryWrapper);
        LinearLayout btnWishlist = findViewById(R.id.btnWishlistWrapper);
        LinearLayout btnSettings = findViewById(R.id.btnSettingsWrapper);
        ImageView btnBack = findViewById(R.id.btnBackProfile);

        // Quay lại trang chủ
        btnBack.setOnClickListener(v -> finish());

        // Các tính năng quản lý
        btnOrderHistory.setOnClickListener(v -> {
            Toast.makeText(this, "Tính năng Lịch sử đơn hàng", Toast.LENGTH_SHORT).show();
        });

        btnWishlist.setOnClickListener(v -> {
            Toast.makeText(this, "Tính năng Sản phẩm yêu thích", Toast.LENGTH_SHORT).show();
        });

        btnSettings.setOnClickListener(v -> {
            Toast.makeText(this, "Tính năng Cài đặt tài khoản", Toast.LENGTH_SHORT).show();
        });

        // Click Đăng xuất -> Quay về màn hình Login
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}