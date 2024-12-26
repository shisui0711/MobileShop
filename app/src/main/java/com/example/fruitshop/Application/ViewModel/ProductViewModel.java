package com.example.fruitshop.Application.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fruitshop.Domain.Entities.Product;
import com.example.fruitshop.Infrastructure.Data.AppDatabase;
import com.example.fruitshop.Infrastructure.Data.DAO.ProductDao;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    ProductDao productDao;
    public ProductViewModel(@NonNull Application application) {
        super(application);
        productDao = AppDatabase.getDatabase(application).productDao();
    }

    public LiveData<List<Product>> getAll(){
       return productDao.getAll();
    }

    public LiveData<List<Product>> getByName(String name){
        return productDao.getByName(name);
    }


}
