package com.example.fruitshop.Presenter.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fruitshop.Application.ViewModel.OrderViewModel;
import com.example.fruitshop.Application.ViewModel.UserViewModel;
import com.example.fruitshop.Domain.Entities.Order;
import com.example.fruitshop.Infrastructure.Tool.Extension;
import com.example.fruitshop.Presenter.Adapter.OrderManagerAdapter;
import com.example.fruitshop.databinding.FragmentOrderManagerBinding;

import java.util.ArrayList;

public class OrderManagerFragment extends Fragment {
    FragmentOrderManagerBinding binding;
    OrderViewModel orderViewModel;
    UserViewModel userViewModel;
    Handler searchHandler = new Handler();
    Runnable searchRunnable;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderManagerBinding.inflate(inflater);
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));

        orderViewModel.getAllOrder().observe(getViewLifecycleOwner(),orders -> binding.recyclerView.setAdapter(new OrderManagerAdapter((ArrayList<Order>) orders,orderViewModel,userViewModel,getViewLifecycleOwner())));

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
        Extension.observeOnce(orderViewModel.getOrderByCustomerName(query),getViewLifecycleOwner(), categories -> binding.recyclerView.setAdapter(new OrderManagerAdapter((ArrayList<Order>) categories,orderViewModel,userViewModel,getViewLifecycleOwner())));
    }
}