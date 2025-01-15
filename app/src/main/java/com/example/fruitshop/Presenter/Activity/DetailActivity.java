package com.example.fruitshop.Presenter.Activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.fruitshop.Application.Model.ProductCart;
import com.example.fruitshop.Domain.Entities.Product;
import com.example.fruitshop.Infrastructure.Data.CartHelper;
import com.example.fruitshop.Presenter.Custom.MyToast;
import com.example.fruitshop.R;
import com.example.fruitshop.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding binding;
    CartHelper cartHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        cartHelper = new CartHelper(this);
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(v-> finish());

        Product product = (Product) getIntent().getSerializableExtra("item");
        binding.txtProductName.setText(product.getName());
        binding.txtDescription.setText(product.getDescription());
        binding.txtUnit.setText(product.getUnit());
        binding.txtProductPrice.setText(product.getPrice() + "đ");

        if(product.getImageUrl().contains("/")){
            Glide.with(this).load(product.getImageUrl())
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(binding.imgProduct);
        }else{
            int drawableResource = getResources().getIdentifier(product.getImageUrl().trim(),
                    "drawable",getPackageName());
            Glide.with(this).load(drawableResource)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(binding.imgProduct);
        }

        binding.btnIncrease.setOnClickListener(v-> {
            int quantity = Integer.parseInt(binding.txtQuantity.getText().toString());
            if(quantity == 99) return;
            binding.txtQuantity.setText(String.valueOf(++quantity));
            binding.txtTotalPrice.setText(String.valueOf(product.getPrice() * quantity) + "đ");
        });

        binding.btnDecrease.setOnClickListener(v-> {
            int quantity = Integer.parseInt(binding.txtQuantity.getText().toString());
            if(quantity == 1) return;
            binding.txtQuantity.setText(String.valueOf(--quantity));
            binding.txtTotalPrice.setText(String.valueOf(product.getPrice() * quantity) + "đ");
        });

        binding.btnAddToCart.setOnClickListener(v->{
            cartHelper.insertProduct(
                    new ProductCart(
                            product.getId(),
                            product.getName(),
                            product.getPrice(),
                            product.getImageUrl(),
                            Integer.parseInt(binding.txtQuantity.getText().toString()),
                            product.getUnit()));
            MyToast.showSuccess(this,"Sản phẩm đã được thêm vào giỏ hàng");
        });
    }
}