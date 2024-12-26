package com.example.fruitshop.Application.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;

import com.example.fruitshop.Domain.Entities.Category;
import com.example.fruitshop.Infrastructure.Data.AppDatabase;
import com.example.fruitshop.Infrastructure.Data.DAO.CategoryDao;

import java.util.List;

import kotlinx.coroutines.flow.Flow;

public class CategoryViewModel extends AndroidViewModel {
    CategoryDao categoryDao;
    public CategoryViewModel(@NonNull Application application) {
        super(application);
        categoryDao = AppDatabase.getDatabase(application).categoryDao();
    }

    public LiveData<List<Category>> getCategories(){
        return categoryDao.getAll();
    }
}
