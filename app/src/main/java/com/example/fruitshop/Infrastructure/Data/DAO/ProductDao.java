package com.example.fruitshop.Infrastructure.Data.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fruitshop.Domain.Entities.Product;
import com.example.fruitshop.Domain.Relations.ProductWithCategory;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert
    void add(Product product);
    @Insert
    void addRange(List<Product> products);
    @Update
    void update(Product product);
    @Delete
    void delete(Product product);
    @Query("SELECT COUNT(*) FROM products")
    int count();
    @Query("SELECT * FROM products WHERE id = :id")

    Product getById(int id);
    @Query("SELECT * FROM products")
    LiveData<List<Product>> getAll();
    @Query("SELECT * FROM products WHERE name LIKE :name")
    LiveData<List<Product>> getByName(String name);

    @Query("SELECT * FROM products")
    ProductWithCategory getAllProductWithCategory();
}
