package com.example.fruitshop.Presenter.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.fruitshop.Application.Model.ProductCart;
import com.example.fruitshop.Application.ViewModel.UserViewModel;
import com.example.fruitshop.Domain.Entities.User;
import com.example.fruitshop.Infrastructure.Data.AppDatabase;
import com.example.fruitshop.Infrastructure.Data.TinyDB;
import com.example.fruitshop.Infrastructure.Data.UserHelper;
import com.example.fruitshop.Infrastructure.Tool.Extension;
import com.example.fruitshop.Presenter.Custom.MyModal;
import com.example.fruitshop.Presenter.Custom.MyToast;
import com.example.fruitshop.Presenter.Fragment.FavoriteFragment;
import com.example.fruitshop.Presenter.Fragment.HomeFragment;
import com.example.fruitshop.Presenter.Fragment.OrderFragment;
import com.example.fruitshop.Presenter.Fragment.ProfileFragment;
import com.example.fruitshop.R;
import com.example.fruitshop.databinding.ActivityMainBinding;
import com.example.fruitshop.databinding.LoginDialogBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    UserHelper userHelper;
    UserViewModel userViewModel;
    Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        userHelper = new UserHelper(this);
        gson = new Gson();
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        setContentView(binding.getRoot());
        checkSignedUser();
        AppDatabase.getDatabase(this);

        getWindow().setStatusBarColor(Color.parseColor("#f5f5f5"));

        getSupportFragmentManager().beginTransaction()
                .replace(binding.frameContainer.getId(), new HomeFragment())
                .commit();

        binding.btnExplore.setOnClickListener(v->{
            getSupportFragmentManager().beginTransaction()
                    .replace(binding.frameContainer.getId(), new HomeFragment())
                    .commit();
            switchTabColor(1);

        });

        binding.btnFavorite.setOnClickListener(v->{
            User user = userHelper.getUserSigned();
            if(user == null){
                MyModal.showLoginDialog(this,()-> startActivity(new Intent(v.getContext(), SignInActivity.class)));
                return;
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(binding.frameContainer.getId(), new FavoriteFragment())
                    .commit();
            switchTabColor(2);
        });


        binding.btnProfile.setOnClickListener(v->{
            User user = userHelper.getUserSigned();
            if(user == null){
                MyModal.showLoginDialog(this,()-> startActivity(new Intent(v.getContext(), SignInActivity.class)));
                return;
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(binding.frameContainer.getId(), new ProfileFragment())
                    .commit();
            switchTabColor(4);
        });

        binding.btnHistory.setOnClickListener(v->{
            User user = userHelper.getUserSigned();
            if(user == null){
                MyModal.showLoginDialog(this,()-> startActivity(new Intent(v.getContext(), SignInActivity.class)));
                return;
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(binding.frameContainer.getId(), new OrderFragment())
                    .commit();
            switchTabColor(3);
        });

        binding.btnCart.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(),CartActivity.class);
            startActivity(intent);
        });
    }

    private void checkSignedUser(){
        User userSigned = userHelper.getUserSigned();
        if(userSigned == null) return;
        Extension.observeOnce(userViewModel.getUserByEmail(userSigned.getEmail()),this,existUser -> {
            if(existUser == null){
                userHelper.removeUser();
            }
        });
    }

    private void switchTabColor(int tabIndex){
        if(tabIndex == 1){
            binding.imgExplore.setImageTintList(ColorStateList.valueOf(Color.parseColor("#0286FF")));
            binding.txtExplore.setTextColor(Color.parseColor("#0286FF"));
            binding.imgFavorite.setImageTintList(ColorStateList.valueOf(Color.parseColor("#818181")));
            binding.txtFavorite.setTextColor(Color.parseColor("#818181"));
            binding.imgOrder.setImageTintList(ColorStateList.valueOf(Color.parseColor("#818181")));
            binding.txtOrder.setTextColor(Color.parseColor("#818181"));
            binding.imgProfile.setImageTintList(ColorStateList.valueOf(Color.parseColor("#818181")));
            binding.txtProfile.setTextColor(Color.parseColor("#818181"));
        } else if (tabIndex ==2) {
            binding.imgExplore.setImageTintList(ColorStateList.valueOf(Color.parseColor("#818181")));
            binding.txtExplore.setTextColor(Color.parseColor("#818181"));
            binding.imgFavorite.setImageTintList(ColorStateList.valueOf(Color.parseColor("#0286FF")));
            binding.txtFavorite.setTextColor(Color.parseColor("#0286FF"));
            binding.imgOrder.setImageTintList(ColorStateList.valueOf(Color.parseColor("#818181")));
            binding.txtOrder.setTextColor(Color.parseColor("#818181"));
            binding.imgProfile.setImageTintList(ColorStateList.valueOf(Color.parseColor("#818181")));
            binding.txtProfile.setTextColor(Color.parseColor("#818181"));
        } else if (tabIndex ==3) {
            binding.imgExplore.setImageTintList(ColorStateList.valueOf(Color.parseColor("#818181")));
            binding.txtExplore.setTextColor(Color.parseColor("#818181"));
            binding.imgFavorite.setImageTintList(ColorStateList.valueOf(Color.parseColor("#818181")));
            binding.txtFavorite.setTextColor(Color.parseColor("#818181"));
            binding.imgOrder.setImageTintList(ColorStateList.valueOf(Color.parseColor("#0286FF")));
            binding.txtOrder.setTextColor(Color.parseColor("#0286FF"));
            binding.imgProfile.setImageTintList(ColorStateList.valueOf(Color.parseColor("#818181")));
            binding.txtProfile.setTextColor(Color.parseColor("#818181"));
        } else if (tabIndex ==4) {
            binding.imgExplore.setImageTintList(ColorStateList.valueOf(Color.parseColor("#818181")));
            binding.txtExplore.setTextColor(Color.parseColor("#818181"));
            binding.imgFavorite.setImageTintList(ColorStateList.valueOf(Color.parseColor("#818181")));
            binding.txtFavorite.setTextColor(Color.parseColor("#818181"));
            binding.imgOrder.setImageTintList(ColorStateList.valueOf(Color.parseColor("#818181")));
            binding.txtOrder.setTextColor(Color.parseColor("#818181"));
            binding.imgProfile.setImageTintList(ColorStateList.valueOf(Color.parseColor("#0286FF")));
            binding.txtProfile.setTextColor(Color.parseColor("#0286FF"));
        }
    }

}