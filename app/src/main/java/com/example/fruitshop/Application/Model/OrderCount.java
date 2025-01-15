package com.example.fruitshop.Application.Model;

public class OrderCount {
    private String date;
    private int orderCount;

    public OrderCount(String date, int orderCount) {
        this.date = date;
        this.orderCount = orderCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }
}
