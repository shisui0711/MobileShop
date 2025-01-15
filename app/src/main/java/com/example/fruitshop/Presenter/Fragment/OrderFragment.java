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
import com.example.fruitshop.Application.ViewModel.ProductViewModel;
import com.example.fruitshop.Domain.Entities.DetailOrder;
import com.example.fruitshop.Domain.Entities.User;
import com.example.fruitshop.Infrastructure.Data.UserHelper;
import com.example.fruitshop.Infrastructure.Tool.Extension;
import com.example.fruitshop.Presenter.Adapter.OrderAdapter;
import com.example.fruitshop.databinding.FragmentOrderBinding;

import java.util.ArrayList;

public class OrderFragment extends Fragment {
    FragmentOrderBinding binding;
    Handler searchHandler = new Handler();
    Runnable searchRunnable;
    OrderViewModel orderViewModel;
    ProductViewModel productViewModel;
    User currentUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderBinding.inflate(inflater);
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        currentUser = new UserHelper(getContext()).getUserSigned();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        orderViewModel.getAllDetailOrderByCustomer(currentUser.getId()).observe(getViewLifecycleOwner(),detailOrders -> {
            binding.recyclerView.setAdapter(new OrderAdapter((ArrayList<DetailOrder>) detailOrders,orderViewModel,productViewModel,getViewLifecycleOwner()));
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
        Extension.observeOnce(orderViewModel.searchDetailOrderByCustomer(currentUser.getId(),query),getViewLifecycleOwner(), de -> {
            binding.recyclerView.setAdapter(new OrderAdapter((ArrayList<DetailOrder>) de,orderViewModel,productViewModel,getViewLifecycleOwner()));
        });
    }
}