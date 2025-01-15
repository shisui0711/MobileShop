package com.example.fruitshop.Presenter.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.fruitshop.Application.ViewModel.CategoryViewModel;
import com.example.fruitshop.Domain.Entities.Category;
import com.example.fruitshop.Infrastructure.Tool.ValidationHelper;
import com.example.fruitshop.Presenter.Activity.UpdateCategoryActivity;
import com.example.fruitshop.Presenter.Custom.MyToast;
import com.example.fruitshop.R;
import com.example.fruitshop.databinding.CategoryManagerViewholderBinding;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class CategoryManagerAdapter extends RecyclerView.Adapter<CategoryManagerAdapter.ViewHolder> {
    Context context;
    List<Category> categories;
    CategoryManagerViewholderBinding binding;
    CategoryViewModel categoryViewModel;

    public CategoryManagerAdapter(List<Category> categories,CategoryViewModel categoryViewModel) {
        this.categories = categories;
        this.categoryViewModel = categoryViewModel;
    }

    @NonNull
    @Override
    public CategoryManagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = CategoryManagerViewholderBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        context = parent.getContext();
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryManagerAdapter.ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.binding.categoryName.setText(category.getName());
        if(category.getImageUrl().contains("/")){
            Glide.with(context).load(category.getImageUrl())
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(holder.binding.categoryImage);
        }else{
            int drawableResource = holder.itemView.getResources().getIdentifier(category.getImageUrl().trim(),
                    "drawable",holder.itemView.getContext().getPackageName());
            Glide.with(context).load(drawableResource)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(holder.binding.categoryImage);
        }


        holder.binding.btnMenu.setOnClickListener(v->{
            PopupMenu popupMenu = new PopupMenu(context,holder.binding.btnMenu);
            popupMenu.inflate(R.menu.update_delete_menu);
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getTitle().toString()) {
                    case "Cập nhật":
                        Intent intent = new Intent(context, UpdateCategoryActivity.class);
                        intent.putExtra("item",category);
                        context.startActivity(intent);
                        return true;
                    case "Xóa":
                        // Thêm logic xóa ở đây
                        categoryViewModel.deleteCategory(category).thenAccept(x->{
                            MyToast.showSuccess(context,"Xóa thành công");
                        });
                        return true;
                }
                return false;
            });

            popupMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CategoryManagerViewholderBinding binding;
        public ViewHolder(@NonNull CategoryManagerViewholderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
