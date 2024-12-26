package com.example.fruitshop.Presenter.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fruitshop.Application.Model.ProductCart;
import com.example.fruitshop.Domain.Entities.Product;
import com.example.fruitshop.Infrastructure.Data.CartHelper;
import com.example.fruitshop.databinding.ProductViewholderBinding;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    ArrayList<Product> products;
    Context context;
    ProductViewholderBinding binding;
    CartHelper cartHelper;

    public ProductAdapter(ArrayList<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ProductViewholderBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        context = parent.getContext();
        cartHelper = new CartHelper(context);
        return new ProductAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        Product product = products.get(position);
        binding.txtProductName.setText(product.getName());
        binding.txtProductPrice.setText(product.getPrice()+"đ");

        int drawableResource = holder.itemView.getResources().getIdentifier(product.getImageUrl(),
                "drawable",holder.itemView.getContext().getPackageName());
        Glide.with(context).load(drawableResource).into(binding.imgProduct);

        binding.btnAddToCart.setOnClickListener(v->{
            cartHelper.insertProduct(
                    new ProductCart(
                            product.getId(),
                            product.getName(),
                            product.getPrice(),
                            product.getImageUrl(),
                            1));
            Toast.makeText(context, "Sản phẩm đã được thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
        });



         binding.imgFavorite.setOnClickListener(v->{

         });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ProductViewholderBinding binding;
        public ViewHolder(@NonNull ProductViewholderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
