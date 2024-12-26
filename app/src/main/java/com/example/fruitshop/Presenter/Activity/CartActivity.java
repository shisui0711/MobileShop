package com.example.fruitshop.Presenter.Activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fruitshop.Infrastructure.Data.CartHelper;
import com.example.fruitshop.Presenter.Adapter.CartAdapter;
import com.example.fruitshop.R;
import com.example.fruitshop.databinding.ActivityCartBinding;

public class CartActivity extends AppCompatActivity {

    ActivityCartBinding binding;
    CartHelper cartHelper;
    boolean codMethod = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        cartHelper = new CartHelper(this);
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(v-> finish());

        binding.cartRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.cartRecyclerView.setAdapter(new CartAdapter(cartHelper.getProductsInCart()));

        binding.btnCOD.setOnClickListener(v->{
            if(codMethod) return;
            codMethod = true;
            binding.btnCOD.setBackground(getResources().getDrawable(R.drawable.bg_primary_selected,null));
            binding.btnTransfer.setBackground(getResources().getDrawable(R.drawable.bg_secondary_selected,null));
        });

        binding.btnTransfer.setOnClickListener(v->{
            if(!codMethod) return;
            codMethod = false;
            binding.btnTransfer.setBackground(getResources().getDrawable(R.drawable.bg_primary_selected,null));
            binding.btnCOD.setBackground(getResources().getDrawable(R.drawable.bg_secondary_selected,null));
        });
    }
}