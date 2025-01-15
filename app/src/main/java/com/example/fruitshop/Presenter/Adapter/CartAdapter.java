package com.example.fruitshop.Presenter.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.fruitshop.Application.Model.ProductCart;
import com.example.fruitshop.Infrastructure.Data.CartHelper;
import com.example.fruitshop.Presenter.Event.OnQuantityChangeListener;
import com.example.fruitshop.databinding.CartViewholderBinding;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    public CartAdapter(OnQuantityChangeListener quantityChangeListener, ArrayList<ProductCart> productCarts) {
        this.quantityChangeListener = quantityChangeListener;
        this.productCarts = productCarts;
    }
    private OnQuantityChangeListener quantityChangeListener;
    ArrayList<ProductCart> productCarts;
    CartViewholderBinding binding;
    Context context;
    CartHelper cartHelper;
    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = CartViewholderBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        context = parent.getContext();
        cartHelper = new CartHelper(context);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        ProductCart productCart = productCarts.get(position);

        holder.binding.txtProductName.setText(productCart.getName());
        holder.binding.txtProductPrice.setText(productCart.getPrice()+"đ");
        holder.binding.txtQuantity.setText(String.valueOf(productCart.getQuantity()));
        holder.binding.txtTotalPrice.setText(String.valueOf(productCart.getPrice() * productCart.getQuantity()) + "đ");
        holder.binding.txtUnit.setText(productCart.getUnit());

        if(productCart.getImageUrl().contains("/")){
            Glide.with(context).load(productCart.getImageUrl())
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(holder.binding.imgProduct);
        }else{
            int drawableResource = holder.itemView.getResources().getIdentifier(productCart.getImageUrl().trim(),
                    "drawable",holder.itemView.getContext().getPackageName());
            Glide.with(context).load(drawableResource)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(holder.binding.imgProduct);
        }

        holder.binding.btnIncrease.setOnClickListener(v-> {
            int quantity = Integer.parseInt(holder.binding.txtQuantity.getText().toString());
            if(quantity == 99) return;
            holder.binding.txtQuantity.setText(String.valueOf(++quantity));
            cartHelper.updateQuantity(productCart,quantity);
            holder.binding.txtTotalPrice.setText(String.valueOf(productCart.getPrice() * quantity) + "đ");
            if(quantityChangeListener != null) quantityChangeListener.onQuantityChanged(productCart);
        });

        holder.binding.btnDecrease.setOnClickListener(v-> {
            int quantity = Integer.parseInt(holder.binding.txtQuantity.getText().toString());
            if(quantity == 1) return;
            holder.binding.txtQuantity.setText(String.valueOf(--quantity));
            cartHelper.updateQuantity(productCart,quantity);
            holder.binding.txtTotalPrice.setText(String.valueOf(productCart.getPrice() * quantity) + "đ");
            if(quantityChangeListener != null) quantityChangeListener.onQuantityChanged(productCart);
        });

        holder.binding.btnDelete.setOnClickListener(v-> {
            productCarts.remove(productCart);
            cartHelper.removeProduct(productCart);
            notifyItemRemoved(position);
            if(quantityChangeListener != null) quantityChangeListener.onQuantityChanged(productCart);
        });
    }

    @Override
    public int getItemCount() {
        return productCarts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CartViewholderBinding binding;
        public ViewHolder(@NonNull CartViewholderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
