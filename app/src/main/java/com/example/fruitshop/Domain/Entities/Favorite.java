package com.example.fruitshop.Domain.Entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "favorites", primaryKeys = {"userId","productId"},
foreignKeys = {
        @ForeignKey(entity = User.class,parentColumns = "id",childColumns = "userId"),
        @ForeignKey(entity = Product.class,parentColumns = "id",childColumns = "productId")
})
public class Favorite {
    private long userId;
    private   int productId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
