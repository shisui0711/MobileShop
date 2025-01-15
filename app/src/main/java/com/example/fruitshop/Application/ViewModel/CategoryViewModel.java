package com.example.fruitshop.Application.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fruitshop.Domain.Entities.Category;
import com.example.fruitshop.Infrastructure.Data.AppDatabase;
import com.example.fruitshop.Infrastructure.Data.DAO.CategoryDao;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CategoryViewModel extends AndroidViewModel {
    CategoryDao categoryDao;
    public CategoryViewModel(@NonNull Application application) {
        super(application);
        categoryDao = AppDatabase.getDatabase(application).categoryDao();
    }

    public CompletableFuture<Void> addCategory(Category category){
        return CompletableFuture.runAsync(()->{
            categoryDao.add(category);
        });
    }


    public CompletableFuture<Void> updateCategory(Category category){
        return CompletableFuture.runAsync(()->{
           categoryDao.update(category);
        });
    }

    public CompletableFuture<Void> deleteCategory(Category category){
        return CompletableFuture.runAsync(()->{
            categoryDao.delete(category);
        });
    }
    public LiveData<Category> getCategoryById(int id){
        return categoryDao.getById(id);
    }

    public LiveData<List<Category>> getCategoriesByName(String name){ return categoryDao.getByName(name);}


    public LiveData<List<Category>> getCategories(){
        return categoryDao.getAll();
    }
}
