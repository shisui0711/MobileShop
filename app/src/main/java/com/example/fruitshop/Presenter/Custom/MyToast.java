package com.example.fruitshop.Presenter.Custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fruitshop.R;
import com.example.fruitshop.databinding.CustomToastBinding;

public class MyToast {
    private static Toast _toast;
    private static CustomToastBinding binding;
    private static Toast getToast(Context context){
        if(_toast == null) {
            _toast = new Toast(context.getApplicationContext());
            binding = CustomToastBinding.inflate(LayoutInflater.from(context.getApplicationContext()));
            _toast.setView(binding.getRoot());
        }
        _toast.setDuration(Toast.LENGTH_SHORT);
        return _toast;
    }
    public static void showSuccess(Context context,String message){
        Toast toast = getToast(context);
        binding.toastText.setText(message);
        Glide.with(context).load(R.drawable.success).into(binding.toastImage);

        toast.show();
    }
    public static void showSuccess(Context context,String message,int duration){
        Toast toast = getToast(context);
        binding.toastText.setText(message);
        Glide.with(context).load(R.drawable.success).into(binding.toastImage);
        toast.setDuration(duration);

        toast.show();
    }

    public static void showError(Context context,String message){
        Toast toast = getToast(context);
        binding.toastText.setText(message);
        Glide.with(context).load(R.drawable.error).into(binding.toastImage);
        toast.show();
    }

    public static void showError(Context context,String message,int duration){
        Toast toast = getToast(context);
        binding.toastText.setText(message);
        Glide.with(context).load(R.drawable.error).into(binding.toastImage);
        toast.setDuration(duration);
        toast.show();
    }

    public static void showWarning(Context context,String message){
        Toast toast = getToast(context);
        binding.toastText.setText(message);
        Glide.with(context).load(R.drawable.warning).into(binding.toastImage);
        toast.show();
    }

    public static void showWarning(Context context,String message,int duration){
        Toast toast = getToast(context);
        binding.toastText.setText(message);
        Glide.with(context).load(R.drawable.warning).into(binding.toastImage);
        toast.setDuration(duration);
        toast.show();
    }
    public static void showInfo(Context context,String message){
        Toast toast = getToast(context);
        binding.toastText.setText(message);
        Glide.with(context).load(R.drawable.bell).into(binding.toastImage);
        toast.show();
    }

    public static void showInfo(Context context,String message, int duration){
        Toast toast = getToast(context);
        binding.toastText.setText(message);
        Glide.with(context).load(R.drawable.bell).into(binding.toastImage);
        toast.setDuration(duration);
        toast.show();
    }
}
