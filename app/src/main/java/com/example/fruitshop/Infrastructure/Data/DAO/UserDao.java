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
import java.util.concurrent.CompletableFuture;

@Dao
public interface UserDao {
    @Insert
    long add(User user);
    @Insert
    void addRange(List<User> users);
    @Update
    void update(User user);
    @Delete
    void delete(User user);
    @Query("SELECT COUNT(*) FROM users")
    int count();
    @Query("SELECT * FROM users WHERE id = :id")

    LiveData<User> getById(long id);
    @Query("SELECT * FROM users WHERE email = :email")
    LiveData<User> getByEmail(String email);
    @Query("SELECT * FROM users")
    LiveData<List<User>> getAll();

}
