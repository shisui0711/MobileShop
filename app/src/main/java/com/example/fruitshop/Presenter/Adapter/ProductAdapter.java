package com.example.fruitshop.Presenter.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.fruitshop.Application.Model.ProductCart;
import com.example.fruitshop.Application.ViewModel.ProductViewModel;
import com.example.fruitshop.Domain.Entities.Product;
import com.example.fruitshop.Infrastructure.Data.CartHelper;
import com.example.fruitshop.Infrastructure.Data.UserHelper;
import com.example.fruitshop.Infrastructure.Tool.Extension;
import com.example.fruitshop.Presenter.Activity.DetailActivity;
import com.example.fruitshop.Presenter.Custom.MyToast;
import com.example.fruitshop.R;
import com.example.fruitshop.databinding.ProductViewholderBinding;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    ArrayList<Product> products;
    Context context;
    ProductViewholderBinding binding;
    CartHelper cartHelper;
    ProductViewModel productViewModel;
    LifecycleOwner lifecycleOwner;
    UserHelper userHelper;

    public ProductAdapter(ArrayList<Product> products,ProductViewModel productViewModel, LifecycleOwner lifecycleOwner) {
        this.products = products;
        this.productViewModel = productViewModel;
        this.lifecycleOwner = lifecycleOwner;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ProductViewholderBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        context = parent.getContext();
        cartHelper = new CartHelper(context);
        userHelper = new UserHelper(context);
        return new ProductAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
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

        holder.binding.btnAddToCart.setOnClickListener(v->{
            cartHelper.insertProduct(
                    new ProductCart(
                            product.getId(),
                            product.getName(),
                            product.getPrice(),
                            product.getImageUrl(),
                            1, product.getUnit()));
            MyToast.showSuccess(context,"Sản phẩm đã được thêm vào giỏ hàng");
        });

        if(userHelper.getUserSigned() == null) return;

        loadFavoriteState(holder,product.getId());
        holder.binding.imgFavorite.setOnClickListener(v->{
            if(userHelper.getUserSigned() == null){
                 return;
             }
            Extension.observeOnce(productViewModel.getFavoriteState(product.getId()),lifecycleOwner,isFavorited -> {
                if (isFavorited) {
                    productViewModel.unFavoriteProduct(product.getId()).thenAccept(x -> {
                        MyToast.showSuccess(context, "Đã xóa khỏi danh sách yêu thích");
                    });
                } else {
                    productViewModel.favoriteProduct(product.getId()).thenAccept(x -> {
                        MyToast.showSuccess(context, "Đã thêm vào danh sách yêu thích");
                    });
                }
            });
         });

        holder.itemView.setOnClickListener(v->{
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("item",product);
            context.startActivity(intent);
        });

    }

    private void loadFavoriteState(ViewHolder holder,int productId){
        productViewModel.getFavoriteState(productId).observe(lifecycleOwner, isFavorited -> {
            if (isFavorited) {
                Glide.with(context).load(R.drawable.heart).override(30,30).into(holder.binding.imgFavorite);
            } else {
                Glide.with(context).load(R.drawable.favorite_green).override(30,30).into(holder.binding.imgFavorite);
            }
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
