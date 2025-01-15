package com.example.fruitshop.Presenter.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fruitshop.Application.ViewModel.ProductViewModel;
import com.example.fruitshop.Domain.Entities.Product;
import com.example.fruitshop.Domain.Entities.User;
import com.example.fruitshop.Infrastructure.Data.UserHelper;
import com.example.fruitshop.Infrastructure.Tool.Extension;
import com.example.fruitshop.Presenter.Adapter.ProductAdapter;
import com.example.fruitshop.R;
import com.example.fruitshop.databinding.FragmentFavoriteBinding;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {
    FragmentFavoriteBinding binding;
    ProductViewModel productViewModel;
    UserHelper userHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        userHelper = new UserHelper(requireContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadCategories();
    }

    private void loadCategories(){
        User user = userHelper.getUserSigned();
        binding.favoriteRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false));
        Extension.observeOnce(productViewModel.getAllFavoritedProducts(user.getId()),getViewLifecycleOwner(),products -> {
            ArrayList<Product> items = (ArrayList<Product>) products;
            binding.favoriteRecyclerView.setAdapter(new ProductAdapter(items,productViewModel,requireActivity()));
        });
    }
}