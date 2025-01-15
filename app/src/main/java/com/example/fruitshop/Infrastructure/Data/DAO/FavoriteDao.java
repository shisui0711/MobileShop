package com.example.fruitshop.Infrastructure.Data.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;

import com.example.fruitshop.Domain.Entities.Favorite;

@Dao
public interface FavoriteDao {
    @Insert
    void add(Favorite favorite);
    @Delete
    void delete(Favorite favorite);
}
