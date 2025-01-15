package com.example.fruitshop.Presenter.Custom;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;

import com.bumptech.glide.Glide;
import com.example.fruitshop.Presenter.Activity.SignInActivity;
import com.example.fruitshop.R;
import com.example.fruitshop.databinding.LoginDialogBinding;
import com.example.fruitshop.databinding.NotifyDialogBinding;

public class MyModal {
    public static void showSuccess(Context context, String message){
        NotifyDialogBinding binding = NotifyDialogBinding.inflate(LayoutInflater.from(context));
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(binding.getRoot());
        dialog.getWindow().setLayout(800,1000);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.edittext_white_bg);
        Glide.with(context).load(R.drawable.success).into(binding.image);
        binding.txtMessage.setText(message);
        binding.btnClose.setOnClickListener(v->dialog.dismiss());
        binding.btnOk.setOnClickListener(v->dialog.dismiss());
        dialog.show();
    }
    public static void showSuccess(Context context, String message, Runnable runnable){
        NotifyDialogBinding binding = NotifyDialogBinding.inflate(LayoutInflater.from(context));
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(binding.getRoot());
        dialog.getWindow().setLayout(800,1000);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.edittext_white_bg);
        Glide.with(context).load(R.drawable.success).into(binding.image);
        binding.txtMessage.setText(message);
        binding.btnClose.setOnClickListener(v->dialog.dismiss());
        binding.btnOk.setOnClickListener(v->{
            dialog.dismiss();
            runnable.run();
        });
        dialog.show();
    }

    public static void showError(Context context, String message){
        NotifyDialogBinding binding = NotifyDialogBinding.inflate(LayoutInflater.from(context));
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(binding.getRoot());
        dialog.getWindow().setLayout(800,1000);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.edittext_white_bg);
        Glide.with(context).load(R.drawable.error).into(binding.image);
        binding.txtMessage.setText(message);
        binding.btnClose.setOnClickListener(v->dialog.dismiss());
        binding.btnOk.setOnClickListener(v->dialog.dismiss());
        dialog.show();
    }
    public static void showError(Context context, String message, Runnable runnable){
        NotifyDialogBinding binding = NotifyDialogBinding.inflate(LayoutInflater.from(context));
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(binding.getRoot());
        dialog.getWindow().setLayout(800,1000);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.edittext_white_bg);
        Glide.with(context).load(R.drawable.error).into(binding.image);
        binding.txtMessage.setText(message);
        binding.btnClose.setOnClickListener(v->dialog.dismiss());
        binding.btnOk.setOnClickListener(v->{
            dialog.dismiss();
            runnable.run();
        });
        dialog.show();
    }

    public static void showLoginDialog(Context context,Runnable runnable){
        LoginDialogBinding loginDialogBinding = LoginDialogBinding.inflate(LayoutInflater.from(context));
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(loginDialogBinding.getRoot());
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.edittext_white_bg);

        loginDialogBinding.btnClose.setOnClickListener(v->dialog.dismiss());
        loginDialogBinding.btnBack.setOnClickListener(v->dialog.dismiss());
        loginDialogBinding.btnLogin.setOnClickListener(v->{
            runnable.run();
        });
        dialog.show();
    }
}
