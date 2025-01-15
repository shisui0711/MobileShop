package com.example.fruitshop.Presenter.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.fruitshop.Application.ViewModel.ProductViewModel;
import com.example.fruitshop.Domain.Entities.Product;
import com.example.fruitshop.Presenter.Activity.UpdateCategoryActivity;
import com.example.fruitshop.Presenter.Activity.UpdateProductActivity;
import com.example.fruitshop.R;
import com.example.fruitshop.databinding.ProductManagerViewholderBinding;

import java.util.List;

public class ProductManagerAdapter extends RecyclerView.Adapter<ProductManagerAdapter.ViewHolder> {
    ProductManagerViewholderBinding binding;
    Context context;
    List<Product> products;
    ProductViewModel productViewModel;

    public ProductManagerAdapter(List<Product> products, ProductViewModel productViewModel) {
        this.products = products;
        this.productViewModel = productViewModel;
    }

    @NonNull
    @Override
    public ProductManagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ProductManagerViewholderBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        context = parent.getContext();
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductManagerAdapter.ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.binding.txtProductName.setText(product.getName());
        holder.binding.txtProductPrice.setText(product.getPrice()+"đ");
        holder.binding.txtUnit.setText(product.getUnit());
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

        holder.binding.btnMenu.setOnClickListener(v->{
            PopupMenu popupMenu = new PopupMenu(context,holder.binding.btnMenu);
            popupMenu.inflate(R.menu.update_delete_menu);
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getTitle().toString()) {
                    case "Cập nhật":
                        Intent intent = new Intent(context, UpdateProductActivity.class);
                        intent.putExtra("item",product);
                        context.startActivity(intent);
                        return true;
                    case "Xóa":
                        // Thêm logic xóa ở đây
                        productViewModel.deleteProduct(product);
                        return true;
                }
                return false;
            });

            popupMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ProductManagerViewholderBinding binding;
        public ViewHolder(@NonNull ProductManagerViewholderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
