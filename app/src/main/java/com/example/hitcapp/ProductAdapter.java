package com.example.hitcapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> productList;
    private OnItemClickListener listener;
    private OnQuantityChangeListener quantityChangeListener;
    private boolean isCart;

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    public interface OnQuantityChangeListener {
        void onQuantityChanged();
    }

    public ProductAdapter(List<Product> productList, OnItemClickListener listener, boolean isCart) {
        this.productList = productList;
        this.listener = listener;
        this.isCart = isCart;
    }

    public void setOnQuantityChangeListener(OnQuantityChangeListener quantityChangeListener) {
        this.quantityChangeListener = quantityChangeListener;
    }

    public void filterList(List<Product> filteredList) {
        this.productList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText(product.getFormattedPrice());
        holder.imgProduct.setImageResource(product.getImageResource());

        if (isCart) {
            // Chế độ giỏ hàng: Hiện tăng/giảm, ẩn nút thêm nhanh
            holder.layoutQuantity.setVisibility(View.VISIBLE);
            holder.btnAddToCart.setVisibility(View.GONE);
            holder.tvQuantity.setText(String.valueOf(product.getQuantity()));

            holder.btnPlus.setOnClickListener(v -> {
                CartManager.incrementQuantity(product.getId());
                notifyItemChanged(position);
                if (quantityChangeListener != null) quantityChangeListener.onQuantityChanged();
            });

            holder.btnMinus.setOnClickListener(v -> {
                CartManager.decrementQuantity(product.getId());
                if (product.getQuantity() <= 0) {
                    if (quantityChangeListener != null) quantityChangeListener.onQuantityChanged();
                } else {
                    notifyItemChanged(position);
                    if (quantityChangeListener != null) quantityChangeListener.onQuantityChanged();
                }
            });
        } else {
            // Chế độ trang chủ: Hiện nút thêm nhanh, ẩn bộ tăng/giảm
            holder.layoutQuantity.setVisibility(View.GONE);
            holder.btnAddToCart.setVisibility(View.VISIBLE);

            holder.btnAddToCart.setOnClickListener(v -> {
                CartManager.addProduct(product);
                Toast.makeText(v.getContext(), "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
            });
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(product));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct, btnAddToCart;
        TextView tvName, tvPrice, tvQuantity;
        LinearLayout layoutQuantity;
        Button btnPlus, btnMinus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
            tvName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvProductPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            layoutQuantity = itemView.findViewById(R.id.layoutQuantity);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnMinus = itemView.findViewById(R.id.btnMinus);
        }
    }
}