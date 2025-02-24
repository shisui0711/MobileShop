package com.example.fruitshop.Presenter.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fruitshop.Application.ViewModel.ProductViewModel;
import com.example.fruitshop.Domain.Entities.Product;
import com.example.fruitshop.Infrastructure.Data.SearchHistoryHelper;
import com.example.fruitshop.Infrastructure.Tool.Extension;
import com.example.fruitshop.Presenter.Adapter.SearchHistoryAdapter;
import com.example.fruitshop.Presenter.Adapter.SearchResultAdapter;
import com.example.fruitshop.R;
import com.example.fruitshop.databinding.ActivitySearchBinding;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    ActivitySearchBinding binding;
    SearchHistoryHelper searchHistoryHelper;
    ProductViewModel productViewModel;
    Handler searchHandler = new Handler();
    Runnable searchRunnable;
    private static final long DEBOUNCE_DELAY = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        searchHistoryHelper = new SearchHistoryHelper(this);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        setContentView(binding.getRoot());

        getWindow().setStatusBarColor(Color.parseColor("#0286FF"));

        binding.btnBack.setOnClickListener(v->finish());

        binding.edtSearch.requestFocus();

        ArrayList<String> histories = searchHistoryHelper.getAll();
        if(histories.size() > 0){
            binding.searchHistoryContainer.setVisibility(View.VISIBLE);
            binding.searchHistoryRecycler.setVisibility(View.VISIBLE);
            binding.searchHistoryRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
            binding.searchHistoryRecycler.setAdapter(new SearchHistoryAdapter(histories));
        }

        binding.searchResultRecycler.setLayoutManager(new LinearLayoutManager(SearchActivity.this,LinearLayoutManager.VERTICAL,false));

        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchHandler.removeCallbacks(searchRunnable);
            }

            @Override
            public void afterTextChanged(Editable s) {
                searchRunnable = () -> performSearch();
                searchHandler.postDelayed(searchRunnable,300);
            }
        });

    }

    private void performSearch() {
        String name = binding.edtSearch.getText().toString();
        Extension.observeOnce(productViewModel.getByName(name),this,data -> {
            binding.searchResultRecycler.setAdapter(new SearchResultAdapter((ArrayList<Product>) data));
        });
    }
}