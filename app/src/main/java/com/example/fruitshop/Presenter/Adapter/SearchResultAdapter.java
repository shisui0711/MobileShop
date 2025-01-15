package com.example.fruitshop.Presenter.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.fruitshop.Domain.Entities.Product;
import com.example.fruitshop.databinding.SearchResultViewholderBinding;

import java.util.ArrayList;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {
    Context context;
    SearchResultViewholderBinding binding;
    ArrayList<Product> products;

    public SearchResultAdapter(ArrayList<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public SearchResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = SearchResultViewholderBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        context = parent.getContext();
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultAdapter.ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.binding.txtProductName.setText(product.getName());
        holder.binding.txtProductPrice.setText(product.getPrice()+"đ");
        if(product.getImageUrl().contains("/")){
            Glide.with(context).load(product.getImageUrl())
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(holder.binding.imgProduct);
        }else{
            int drawableResource = holder.itemView.getResources().getIdentifier(product.getImageUrl().trim(),
                    "drawable",holder.itemView.getContext().getPackageName());
            Glide.with(context).load(drawableResource)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(holder.binding.imgProduct);
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SearchResultViewholderBinding binding;
        public ViewHolder(@NonNull SearchResultViewholderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
