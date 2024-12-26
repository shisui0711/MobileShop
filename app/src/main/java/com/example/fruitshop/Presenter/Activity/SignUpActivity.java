package com.example.fruitshop.Presenter.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.fruitshop.Application.ViewModel.UserViewModel;
import com.example.fruitshop.Domain.Entities.User;
import com.example.fruitshop.Infrastructure.Data.UserHelper;
import com.example.fruitshop.Presenter.Custom.MyToast;
import com.example.fruitshop.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    private UserViewModel userViewModel;
    UserHelper userHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userHelper = new UserHelper(this);
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(v->{
            Intent intent = new Intent(this, SignInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            finish();
        });

        binding.btnRegister.setOnClickListener(this::signUp);
    }

    private void signUp(View v){
        String name = binding.edtFullName.getText().toString();
        String email = binding.edtEmail.getText().toString();
        String password = binding.edtPassword.getText().toString();
        userViewModel.signUp(name,email,password,this).observe(this, result -> {
            if(result.isLoading()) return;
            if(result.getErrors().size() > 0){
                MyToast.showError(SignUpActivity.this,result.getErrors().iterator().next());
            }else{
                userHelper.saveUser(result.getData());
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }

        });

    }

}