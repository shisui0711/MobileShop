package com.example.fruitshop.Presenter.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fruitshop.Domain.Entities.Category;
import com.example.fruitshop.databinding.CategoryViewholderBinding;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    ArrayList<Category> categories;
    Context context;
    CategoryViewholderBinding binding;
    public CategoryAdapter(ArrayList<Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = CategoryViewholderBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        context = parent.getContext();
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        Category category = categories.get(position);
        binding.categoryName.setText(category.getName());

        int drawableResource = holder.itemView.getResources().getIdentifier(category.getImageUrl(),
                "drawable",holder.itemView.getContext().getPackageName());
        Glide.with(context).load(drawableResource).into(binding.categoryImage);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final CategoryViewholderBinding binding;

        public ViewHolder(@NonNull CategoryViewholderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
