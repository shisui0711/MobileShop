package com.example.fruitshop.Infrastructure.Data.DAO;

import androidx.lifecycle.LiveData;
import androidx.paging.PagingSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fruitshop.Domain.Entities.Category;
import com.example.fruitshop.Domain.Relations.CategoryWithProducts;


import java.util.List;

@Dao
public interface CategoryDao {
    @Insert
    void add(Category category);
    @Insert
    void addRange(List<Category> categories);
    @Update
    void update(Category category);
    @Delete
    void delete(Category category);
    @Query("SELECT COUNT(*) FROM categories")
    int count();
    @Query("SELECT * FROM categories WHERE id = :id")

    Category getById(int id);
    @Query("SELECT * FROM categories")
    LiveData<List<Category>> getAll();

    @Query("SELECT * FROM categories")
    CategoryWithProducts getAllCategoryWithProducts();
}
