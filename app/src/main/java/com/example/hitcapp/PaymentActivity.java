package com.example.hitcapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        TextView tvTotalAmount = findViewById(R.id.tvTotalPricePayment);
        Button btnConfirm = findViewById(R.id.btnConfirmPayment);
        ImageView btnBack = findViewById(R.id.btnBackPayment);
        
        EditText edtPhone = findViewById(R.id.edtPhone);
        EditText edtEmail = findViewById(R.id.edtEmailPayment);
        EditText edtAddress = findViewById(R.id.edtAddress);
        RadioButton rbCOD = findViewById(R.id.rbCOD);

        // Nhận tổng tiền từ CartActivity
        double totalPrice = getIntent().getDoubleExtra("total_price", 0);
        String formattedPrice = String.format("%,.0f VNĐ", totalPrice);
        tvTotalAmount.setText(formattedPrice);

        btnBack.setOnClickListener(v -> finish());

        btnConfirm.setOnClickListener(v -> {
            String phone = edtPhone.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String address = edtAddress.getText().toString().trim();

            if (phone.isEmpty() || email.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin giao hàng!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Xử lý logic đặt hàng thành công
            showSuccessDialog();
        });
    }

    private void showSuccessDialog() {
        // 1. Khởi tạo Dialog với giao diện tùy chỉnh
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_order_success, null);
        builder.setView(dialogView);
        
        AlertDialog dialog = builder.create();
        
        // Làm cho nền Dialog trong suốt để thấy bo góc của CardView
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        // 2. Thiết lập nút quay lại mua sắm
        Button btnContinue = dialogView.findViewById(R.id.btnContinueShopping);
        btnContinue.setOnClickListener(v -> {
            // Xóa giỏ hàng sau khi mua thành công
            CartManager.clearCart();
            
            // Quay về Home và xóa các Activity cũ
            Intent intent = new Intent(PaymentActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            
            dialog.dismiss();
            finish();
        });

        dialog.setCancelable(false); // Không cho tắt dialog khi chưa bấm nút
        dialog.show();
    }
}