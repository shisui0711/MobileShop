package com.example.fruitshop.Presenter.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.fruitshop.Application.ViewModel.CategoryViewModel;
import com.example.fruitshop.Domain.Entities.Category;
import com.example.fruitshop.Presenter.Adapter.CategoryAdapter;
import com.example.fruitshop.R;
import com.example.fruitshop.databinding.ActivityCategoryBinding;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    ActivityCategoryBinding binding;
    CategoryViewModel categoryViewModel;
    ArrayList<Category> categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(v->finish());

        categories = new ArrayList<>();
        CategoryAdapter adapter = new CategoryAdapter(categories);
        binding.categoryRecylerView.setLayoutManager(new GridLayoutManager(this,4,GridLayoutManager.VERTICAL,false));
        binding.categoryRecylerView.setAdapter(adapter);

        categoryViewModel.getCategories().observe(this, categories -> {
            this.categories.addAll(categories);
            adapter.notifyDataSetChanged();
            binding.progressBar.setVisibility(View.INVISIBLE);
        });

    }
}