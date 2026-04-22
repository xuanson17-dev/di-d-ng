package com.example.hitcapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView tvRegister = findViewById(R.id.tvRegister);

        // Khởi tạo SharedPreferences để đọc dữ liệu đã đăng ký
        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        // Click Đăng nhập
        btnLogin.setOnClickListener(v -> {
            if (validateInput()) {
                performLogin();
            }
        });

        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void performLogin() {
        String emailInput = edtEmail.getText().toString().trim();
        String passInput = edtPassword.getText().toString().trim();

        // 1. Dữ liệu cứng (Admin)
        String adminEmail = "admin@gmail.com";
        String adminPass = "123456";

        // 2. Lấy dữ liệu từ SharedPreferences (Tài khoản vừa đăng ký)
        String registeredEmail = sharedPreferences.getString("email", "");
        String registeredPass = sharedPreferences.getString("password", "");

        // Kiểm tra điều kiện
        if ((emailInput.equals(adminEmail) && passInput.equals(adminPass)) ||
            (emailInput.equals(registeredEmail) && passInput.equals(registeredPass))) {
            
            Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Email hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateInput() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            edtEmail.setError("Vui lòng nhập Email");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Email không hợp lệ");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Vui lòng nhập mật khẩu");
            return false;
        }
        return true;
    }
}