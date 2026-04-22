package com.example.hitcapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword, edtConfirmPassword;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        Button btnRegister = findViewById(R.id.btnRegister);
        TextView tvBackToLogin = findViewById(R.id.tvBackToLogin);

        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        btnRegister.setOnClickListener(v -> {
            if (validateRegistration()) {
                saveUserData();
            }
        });

        tvBackToLogin.setOnClickListener(v -> finish());
        
        if (findViewById(R.id.btnBackRegister) != null) {
            findViewById(R.id.btnBackRegister).setOnClickListener(v -> finish());
        }
    }

    private void saveUserData() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply(); // Lưu dữ liệu vào bộ nhớ máy

        Toast.makeText(this, "Đăng ký thành công tài khoản: " + email, Toast.LENGTH_SHORT).show();
        finish(); // Quay lại màn hình Login
    }

    private boolean validateRegistration() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            edtEmail.setError("Vui lòng nhập Email");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Email không hợp lệ");
            return false;
        }
        if (password.length() < 6) {
            edtPassword.setError("Mật khẩu phải từ 6 ký tự");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            edtConfirmPassword.setError("Mật khẩu xác nhận không khớp");
            return false;
        }
        return true;
    }
}