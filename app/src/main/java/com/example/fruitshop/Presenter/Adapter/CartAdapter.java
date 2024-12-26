package com.example.fruitshop.Presenter.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fruitshop.Application.Model.ProductCart;
import com.example.fruitshop.databinding.CartViewholderBinding;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    public CartAdapter(ArrayList<ProductCart> productCarts) {
        this.productCarts = productCarts;
    }

    ArrayList<ProductCart> productCarts;
    CartViewholderBinding binding;
    Context context;
    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = CartViewholderBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        context = parent.getContext();
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        ProductCart productCart = productCarts.get(position);

        binding.txtProductName.setText(productCart.getName());
        binding.txtProductPrice.setText(productCart.getPrice()+"đ");
        binding.txtQuantity.setText(String.valueOf(productCart.getQuantity()));
        binding.txtTotalPrice.setText(String.valueOf(productCart.getPrice() * productCart.getQuantity()));

        int drawableResource = holder.itemView.getResources().getIdentifier(productCart.getImageUrl(),
                "drawable",holder.itemView.getContext().getPackageName());

        Glide.with(context).load(drawableResource).into(binding.imgProduct);

        binding.btnIncrease.setOnClickListener(v-> {
            int quantity = Integer.parseInt(binding.txtQuantity.getText().toString());
            if(quantity == 99) return;
            binding.txtQuantity.setText(String.valueOf(++quantity));
            binding.txtTotalPrice.setText(String.valueOf(productCart.getPrice() * quantity));
        });

        binding.btnDecrease.setOnClickListener(v-> {
            int quantity = Integer.parseInt(binding.txtQuantity.getText().toString());
            if(quantity == 1) return;
            binding.txtQuantity.setText(String.valueOf(--quantity));
            binding.txtTotalPrice.setText(String.valueOf(productCart.getPrice() * quantity));
        });
    }

    @Override
    public int getItemCount() {
        return productCarts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull CartViewholderBinding binding) {
            super(binding.getRoot());
        }
    }
}
