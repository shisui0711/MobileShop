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

import com.example.fruitshop.Application.ViewModel.CategoryViewModel;
import com.example.fruitshop.Infrastructure.Tool.Extension;
import com.example.fruitshop.Presenter.Activity.CreateCategoryActivity;
import com.example.fruitshop.Presenter.Adapter.CategoryManagerAdapter;
import com.example.fruitshop.Presenter.Adapter.ProductManagerAdapter;
import com.example.fruitshop.R;
import com.example.fruitshop.databinding.FragmentCategoryManagerBinding;

public class CategoryManagerFragment extends Fragment {
    FragmentCategoryManagerBinding binding;
    CategoryViewModel categoryViewModel;
    Handler searchHandler = new Handler();
    Runnable searchRunnable;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoryManagerBinding.inflate(inflater);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        super.onViewCreated(view, savedInstanceState);

        binding.btnCreate.setOnClickListener(v->{
            startActivity(new Intent(requireContext(), CreateCategoryActivity.class));
        });

        categoryViewModel.getCategories().observe(getViewLifecycleOwner(),categories -> {
            binding.recyclerView.setAdapter(new CategoryManagerAdapter(categories,categoryViewModel));
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
                searchHandler.postDelayed(searchRunnable,300);
            }
        });
    }

    private void performSearch() {
        String query = binding.edtSearch.getText().toString();
        if (query.equals("")){
            Extension.observeOnce(categoryViewModel.getCategories(),getViewLifecycleOwner(),categories -> {
                binding.recyclerView.setAdapter(new CategoryManagerAdapter(categories,categoryViewModel));
            });
            return;
        }
        Extension.observeOnce(categoryViewModel.getCategoriesByName(query),getViewLifecycleOwner(), categories -> {
            binding.recyclerView.setAdapter(new CategoryManagerAdapter(categories,categoryViewModel));
        });
    }
}