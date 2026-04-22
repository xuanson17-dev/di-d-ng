package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private List<Product> products;
    private List<Product> currentFilteredList;
    private ProductAdapter adapter;
    private EditText edtSearch;
    private ViewPager2 viewPagerBanner;
    private Handler sliderHandler = new Handler(Looper.getMainLooper());
    private int bannerSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        RecyclerView rvProducts = findViewById(R.id.rvProducts);
        ImageView imgCart = findViewById(R.id.imgCart);
        ImageView imgProfile = findViewById(R.id.imgProfile);
        edtSearch = findViewById(R.id.edtSearch);
        Spinner spnSort = findViewById(R.id.spnSort);
        viewPagerBanner = findViewById(R.id.viewPagerBanner);

        // Setup Banner Slider
        List<Integer> bannerImages = new ArrayList<>();
        bannerImages.add(R.drawable.banner1);
        bannerImages.add(R.drawable.banner2);
        bannerSize = bannerImages.size();

        BannerAdapter bannerAdapter = new BannerAdapter(bannerImages);
        viewPagerBanner.setAdapter(bannerAdapter);

        viewPagerBanner.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000);
            }
        });

        rvProducts.setLayoutManager(new GridLayoutManager(this, 2));

        // Dữ liệu sản phẩm
        loadProducts();

        currentFilteredList = new ArrayList<>(products);

        adapter = new ProductAdapter(currentFilteredList, product -> {
            Intent intent = new Intent(HomeActivity.this, DetailActivity.class);
            intent.putExtra("product_data", product);
            startActivity(intent);
        }, false);

        rvProducts.setAdapter(adapter);

        // Setup Category Clicks
        findViewById(R.id.catAll).setOnClickListener(v -> filterByCategory("All"));
        findViewById(R.id.catIphone).setOnClickListener(v -> filterByCategory("iPhone"));
        findViewById(R.id.catMacbook).setOnClickListener(v -> filterByCategory("MacBook"));
        findViewById(R.id.catCamera).setOnClickListener(v -> filterByCategory("Camera"));
        findViewById(R.id.catAccessories).setOnClickListener(v -> filterByCategory("Accessories"));

        // Setup Sorting
        String[] sortOptions = {"Sắp xếp", "Tên A-Z", "Tên Z-A", "Giá thấp", "Giá cao"};
        ArrayAdapter<String> sortAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, sortOptions);
        sortAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spnSort.setAdapter(sortAdapter);

        spnSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortList(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // SỬA LỖI QUAN TRỌNG: Mở trang Giỏ hàng và Trang cá nhân
        imgCart.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
        });

        imgProfile.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadProducts() {
        products = new ArrayList<>();
        products.add(new Product("1", "iPhone 17 Ultra", 35000000.0, R.drawable.iphone17, "Apple iPhone 17 Ultra - Siêu phẩm tương lai.", "iPhone"));
        products.add(new Product("2", "MacBook Pro M3", 45500000.0, R.drawable.macbook, "MacBook Pro M3 - Hiệu năng cực đỉnh.", "MacBook"));
        products.add(new Product("3", "iPad Pro M4", 28900000.0, R.drawable.ipad, "iPad Pro M4 - Màn hình OLED cực nét.", "iPad"));
        products.add(new Product("4", "Canon EOS R5", 85000000.0, R.drawable.cannon, "Canon EOS R5 - Quay phim 8K chuyên nghiệp.", "Camera"));
        products.add(new Product("5", "Canon EOS R6 II", 55000000.0, R.drawable.cannon1, "Canon EOS R6 Mark II - Lấy nét cực nhanh.", "Camera"));
        products.add(new Product("6", "AirPods Pro 2", 5900000.0, R.drawable.airpods, "AirPods Pro Gen 2 - Chống ồn đỉnh cao.", "Accessories"));
    }

    private void filterByCategory(String category) {
        currentFilteredList.clear();
        if (category.equals("All")) {
            currentFilteredList.addAll(products);
        } else {
            for (Product p : products) {
                if (p.getCategory().equalsIgnoreCase(category)) {
                    currentFilteredList.add(p);
                }
            }
        }
        adapter.filterList(currentFilteredList);
    }

    private void filter(String text) {
        currentFilteredList.clear();
        if (text.isEmpty()) {
            currentFilteredList.addAll(products);
        } else {
            for (Product item : products) {
                if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                    currentFilteredList.add(item);
                }
            }
        }
        adapter.filterList(currentFilteredList);
    }

    private void sortList(int position) {
        if (position == 0) return;
        switch (position) {
            case 1: Collections.sort(currentFilteredList, (p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName())); break;
            case 2: Collections.sort(currentFilteredList, (p1, p2) -> p2.getName().compareToIgnoreCase(p1.getName())); break;
            case 3: Collections.sort(currentFilteredList, (p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice())); break;
            case 4: Collections.sort(currentFilteredList, (p1, p2) -> Double.compare(p2.getPrice(), p1.getPrice())); break;
        }
        adapter.notifyDataSetChanged();
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            if (bannerSize > 0) {
                int nextItem = viewPagerBanner.getCurrentItem() + 1;
                if (nextItem >= bannerSize) nextItem = 0;
                viewPagerBanner.setCurrentItem(nextItem);
            }
        }
    };

    @Override
    protected void onPause() { super.onPause(); sliderHandler.removeCallbacks(sliderRunnable); }
    @Override
    protected void onResume() { super.onResume(); sliderHandler.postDelayed(sliderRunnable, 3000); }
}