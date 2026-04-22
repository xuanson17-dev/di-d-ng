package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private ImageView imgDetail;
    private TextView tvName, tvPrice, tvDescription;
    private Product currentProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Ánh xạ View
        imgDetail = findViewById(R.id.imgDetail);
        tvName = findViewById(R.id.tvDetailName);
        tvPrice = findViewById(R.id.tvDetailPrice);
        tvDescription = findViewById(R.id.tvDetailDescription);
        Button btnAddToCart = findViewById(R.id.btnAddToCart);
        Button btnBuyNow = findViewById(R.id.btnBuyNow);
        ImageView btnBack = findViewById(R.id.btnBackDetail);
        RecyclerView rvRelated = findViewById(R.id.rvRelatedProducts);

        // Nhận dữ liệu
        currentProduct = (Product) getIntent().getSerializableExtra("product_data");
        if (currentProduct != null) {
            updateUI(currentProduct);
            setupRelatedProducts(rvRelated);
        }

        btnBack.setOnClickListener(v -> finish());

        // FIX: Bấm vào Giỏ hàng -> Thêm và chuyển sang trang CartActivity
        btnAddToCart.setOnClickListener(v -> {
            if (currentProduct != null) {
                CartManager.addProduct(currentProduct);
                Toast.makeText(this, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                
                // Mở trang Giỏ hàng để chỉnh sửa số lượng
                Intent intent = new Intent(DetailActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        // Bấm Mua ngay -> Thêm và chuyển thẳng tới trang Thanh toán (Payment)
        btnBuyNow.setOnClickListener(v -> {
            if (currentProduct != null) {
                CartManager.addProduct(currentProduct);
                Intent intent = new Intent(DetailActivity.this, PaymentActivity.class);
                intent.putExtra("total_price", currentProduct.getPrice());
                startActivity(intent);
            }
        });
    }

    private void updateUI(Product product) {
        imgDetail.setImageResource(product.getImageResource());
        tvName.setText(product.getName());
        tvPrice.setText(product.getFormattedPrice());
        tvDescription.setText(product.getDescription());
        // Cuộn lên đầu trang khi đổi sản phẩm
        findViewById(R.id.scrollDetail).scrollTo(0, 0);
    }

    private void setupRelatedProducts(RecyclerView rv) {
        List<Product> allProducts = getFullProductList();
        List<Product> related = new ArrayList<>();

        for (Product p : allProducts) {
            if (p.getCategory().equalsIgnoreCase(currentProduct.getCategory()) 
                && !p.getId().equals(currentProduct.getId())) {
                related.add(p);
            }
        }

        ProductAdapter relatedAdapter = new ProductAdapter(related, product -> {
            currentProduct = product;
            updateUI(currentProduct);
            setupRelatedProducts(rv);
        }, false);

        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv.setAdapter(relatedAdapter);
    }

    private List<Product> getFullProductList() {
        List<Product> list = new ArrayList<>();
        list.add(new Product("1", "iPhone 17 Ultra", 35000000.0, R.drawable.iphone17, "Apple iPhone 17 Ultra - Siêu phẩm tương lai.", "iPhone"));
        list.add(new Product("2", "MacBook Pro M3", 45500000.0, R.drawable.macbook, "MacBook Pro M3 - Hiệu năng cực đỉnh.", "MacBook"));
        list.add(new Product("3", "iPad Pro M4", 28900000.0, R.drawable.ipad, "iPad Pro M4 - Màn hình OLED cực nét.", "iPad"));
        list.add(new Product("4", "Canon EOS R5", 85000000.0, R.drawable.cannon, "Canon EOS R5 - Quay phim 8K chuyên nghiệp.", "Camera"));
        list.add(new Product("5", "Canon EOS R6 II", 55000000.0, R.drawable.cannon1, "Canon EOS R6 Mark II - Lấy nét cực nhanh.", "Camera"));
        list.add(new Product("6", "AirPods Pro 2", 5900000.0, R.drawable.airpods, "AirPods Pro Gen 2 - Chống ồn đỉnh cao.", "Accessories"));
        return list;
    }
}