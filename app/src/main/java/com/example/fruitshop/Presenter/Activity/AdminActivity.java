package com.example.fruitshop.Presenter.Activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fruitshop.Presenter.Fragment.CategoryManagerFragment;
import com.example.fruitshop.Presenter.Fragment.OrderManagerFragment;
import com.example.fruitshop.Presenter.Fragment.ProductManagerFragment;
import com.example.fruitshop.Presenter.Fragment.StatisticsFragment;
import com.example.fruitshop.R;
import com.example.fruitshop.databinding.ActivityAdminBinding;
import com.google.android.material.navigation.NavigationView;

public class AdminActivity extends AppCompatActivity {
    ActivityAdminBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction()
                .replace(binding.frameContainer.getId(), new StatisticsFragment())
                .commit();

        binding.btnMenu.setOnClickListener(v->binding.main.openDrawer(binding.navView));

        binding.navView.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getTitle().toString()){
                case "Trang chủ":
                    getSupportFragmentManager().beginTransaction()
                            .replace(binding.frameContainer.getId(), new StatisticsFragment())
                            .commit();
                    break;
                case "Sản phẩm":
                    getSupportFragmentManager().beginTransaction()
                            .replace(binding.frameContainer.getId(), new ProductManagerFragment())
                            .commit();
                    break;
                case "Danh mục":
                    getSupportFragmentManager().beginTransaction()
                            .replace(binding.frameContainer.getId(), new CategoryManagerFragment())
                            .commit();
                    break;
                case "Đơn hàng":
                    getSupportFragmentManager().beginTransaction()
                            .replace(binding.frameContainer.getId(), new OrderManagerFragment())
                            .commit();
                    break;
                case "Về trang chủ":
                    finish();
                    break;
                default:
                    break;
            }
            binding.main.closeDrawers();
            return true;
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}