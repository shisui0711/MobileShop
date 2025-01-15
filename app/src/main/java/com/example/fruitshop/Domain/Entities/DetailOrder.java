package com.example.fruitshop.Domain.Entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "detail_orders",
primaryKeys = {"orderId","productId"},
foreignKeys = {
        @ForeignKey(
                entity = Order.class,
                parentColumns = "id",
                childColumns = "orderId",
                onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity = Product.class,
                parentColumns = "id",
                childColumns = "productId",
                onDelete = ForeignKey.CASCADE
        )
})
public class DetailOrder {
    private long orderId;
    private int productId;
    private int quantity;
    private int price;

    public DetailOrder(int productId, int quantity, int price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
