package com.example.fruitshop.Presenter.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fruitshop.Application.ViewModel.CategoryViewModel;
import com.example.fruitshop.Application.ViewModel.ProductViewModel;
import com.example.fruitshop.Domain.Entities.Category;
import com.example.fruitshop.Domain.Entities.Product;
import com.example.fruitshop.Domain.Entities.User;
import com.example.fruitshop.Infrastructure.Data.UserHelper;
import com.example.fruitshop.Presenter.Activity.CategoryActivity;
import com.example.fruitshop.Presenter.Activity.MainActivity;
import com.example.fruitshop.Presenter.Activity.SearchActivity;
import com.example.fruitshop.Presenter.Adapter.CategoryAdapter;
import com.example.fruitshop.Presenter.Adapter.ProductAdapter;
import com.example.fruitshop.R;
import com.example.fruitshop.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    UserHelper userHelper;
    CategoryViewModel categoryViewModel;
    ProductViewModel productViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        userHelper = new UserHelper(requireContext());
        binding = FragmentHomeBinding.inflate(inflater);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        User user = userHelper.getUserSigned();
        if(user != null)
            binding.userName.setText(userHelper.getUserSigned().getName());
        binding.btnSeeAllCategory.setOnClickListener(v->{
            startActivity(new Intent(requireContext(), CategoryActivity.class));
        });
        binding.btnSearch.setOnClickListener(v->startActivity(new Intent(requireContext(), SearchActivity.class)));
        initCategoryRecylerView();
        initProductRecylerView();
    }

    private void initProductRecylerView(){
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(1,"Cam",2,10000,10,"orange"));
        products.add(new Product(2,"Dứa",2,10000,10,"pineapple"));
        products.add(new Product(3,"Dưa hấu",2,10000,10,"watermelon"));
        products.add(new Product(4,"Dâu tây",2,10000,10,"strawberry"));

        productViewModel.getAll().observe(requireActivity(),data ->{
            if(data.size() > 0){
                products.clear();
                products.addAll(data);
            }
        });
        binding.suggestionView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false));
        binding.suggestionView.setAdapter(new ProductAdapter(products));
    }

    private void initCategoryRecylerView(){
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category(1,"Rau củ","cat1"));
        categories.add(new Category(2,"Hoa quả","cat2"));
        categories.add(new Category(3,"Sữa","cat3"));
        categories.add(new Category(4,"Đồ uống","cat4"));
        categories.add(new Category(5,"Đồ ăn","cat5"));

        categoryViewModel.getCategories().observe(requireActivity(),data -> {
            if(data.size() > 0){
                categories.clear();
                categories.addAll(data);
            }
        });

        binding.categoryView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false));
        binding.categoryView.setAdapter(new CategoryAdapter(categories));

    }
}