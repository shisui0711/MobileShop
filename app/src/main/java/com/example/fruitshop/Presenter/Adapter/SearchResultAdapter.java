package com.example.fruitshop.Presenter.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
        binding.txtProductName.setText(product.getName());
        binding.txtProductPrice.setText(product.getPrice()+"đ");
        int drawableResource = holder.itemView.getResources().getIdentifier(product.getImageUrl(),
                "drawable",holder.itemView.getContext().getPackageName());
        Glide.with(context).load(drawableResource).into(binding.imgProduct);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull SearchResultViewholderBinding binding) {
            super(binding.getRoot());
        }
    }
}
