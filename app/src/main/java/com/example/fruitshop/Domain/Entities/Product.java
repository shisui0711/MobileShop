package com.example.fruitshop.Domain.Entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(
    tableName = "products",
    foreignKeys = @ForeignKey(
            entity = Category.class,
            parentColumns = "id",
            childColumns = "categoryId",
            onDelete = ForeignKey.CASCADE
    )
)
public class Product implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int categoryId;
    private int price;
    private String imageUrl;
    private String unit;
    private String description;


    public Product() {
    }

    public Product(int id, String name, int categoryId, int price, String imageUrl, String unit, String description) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.price = price;
        this.imageUrl = imageUrl;
        this.unit = unit;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
