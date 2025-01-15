package com.example.fruitshop.Presenter.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fruitshop.Application.ViewModel.ProductViewModel;
import com.example.fruitshop.Infrastructure.Tool.Extension;
import com.example.fruitshop.Presenter.Activity.CreateCategoryActivity;
import com.example.fruitshop.Presenter.Activity.CreateProductActivity;
import com.example.fruitshop.Presenter.Adapter.ProductManagerAdapter;
import com.example.fruitshop.R;
import com.example.fruitshop.databinding.FragmentProductManagerBinding;

public class ProductManagerFragment extends Fragment {

    FragmentProductManagerBinding binding;
    ProductViewModel productViewModel;
    Handler searchHandler = new Handler();
    Runnable searchRunnable;
    private static final long DEBOUNCE_DELAY = 300;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductManagerBinding.inflate(inflater);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));

        binding.btnCreate.setOnClickListener(v->{
            startActivity(new Intent(requireContext(), CreateProductActivity.class));
        });
        productViewModel.getAll().observe(getViewLifecycleOwner(),products -> {
            binding.recyclerView.setAdapter(new ProductManagerAdapter(products,productViewModel));
        });

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
                searchHandler.postDelayed(searchRunnable,DEBOUNCE_DELAY);
            }
        });
    }

    private void performSearch() {
        String query = binding.edtSearch.getText().toString();
        if (query.equals("")){
            Extension.observeOnce(productViewModel.getAll(),getViewLifecycleOwner(),products -> {
                binding.recyclerView.setAdapter(new ProductManagerAdapter(products,productViewModel));
            });
            return;
        }
        Extension.observeOnce(productViewModel.getByName(query),getViewLifecycleOwner(),products -> {
            binding.recyclerView.setAdapter(new ProductManagerAdapter(products,productViewModel));
        });
    }
}