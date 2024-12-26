package com.example.fruitshop.Domain.Entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "favorites", primaryKeys = {"userId","productId"},
foreignKeys = {
        @ForeignKey(entity = User.class,parentColumns = "id",childColumns = "userId"),
        @ForeignKey(entity = Product.class,parentColumns = "id",childColumns = "productId")
})
public class Favorite {
    public int userId;
    public  int productId;
}
