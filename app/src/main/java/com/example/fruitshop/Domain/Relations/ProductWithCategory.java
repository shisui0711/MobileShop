package com.example.fruitshop.Domain.Relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.fruitshop.Domain.Entities.Category;
import com.example.fruitshop.Domain.Entities.Product;

public class ProductWithCategory {
    @Embedded
    private Product product;
    @Relation(parentColumn = "categoryId", entityColumn = "id")
    private Category category;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
