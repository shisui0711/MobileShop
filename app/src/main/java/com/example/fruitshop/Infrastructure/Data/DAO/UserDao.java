package com.example.fruitshop.Infrastructure.Data.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fruitshop.Domain.Entities.Product;
import com.example.fruitshop.Domain.Entities.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void add(User user);
    @Insert
    void addRange(List<User> users);
    @Update
    void update(User user);
    @Delete
    void delete(User user);
    @Query("SELECT COUNT(*) FROM users")
    int count();
    @Query("SELECT * FROM users WHERE id = :id")

    User getById(int id);
    @Query("SELECT * FROM users WHERE email = :email")
    LiveData<User> getByEmail(String email);
    @Query("SELECT * FROM users")
    List<User> getAll();
    @Query("SELECT * FROM products INNER JOIN favorites ON products.id = favorites.productId WHERE favorites.userId = :userId")
    List<Product> getFavoriteProducts(int userId);
}
