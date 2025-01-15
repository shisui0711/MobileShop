package com.example.fruitshop.Application.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fruitshop.Application.Model.OrderCount;
import com.example.fruitshop.Domain.Entities.DetailOrder;
import com.example.fruitshop.Domain.Entities.Order;
import com.example.fruitshop.Infrastructure.Data.AppDatabase;
import com.example.fruitshop.Infrastructure.Data.DAO.OrderDao;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class OrderViewModel extends AndroidViewModel {

    OrderDao orderDao;
    public OrderViewModel(@NonNull Application application) {
        super(application);
        orderDao = AppDatabase.getDatabase(application).orderDao();
    }

    public CompletableFuture<Void> createOrder(Order order, List<DetailOrder> detailOrders){
        return CompletableFuture.runAsync(()->{
            orderDao.addOrder(order,detailOrders);
        });
    }

    public CompletableFuture<Void> deleteOrder(Order order){
        return  CompletableFuture.runAsync(()->{
           orderDao.delete(order);
        });
    }
    public LiveData<Order> getOrderById(long orderId){
        return orderDao.getById(orderId);
    }

    public LiveData<List<Order>> getAllOrder(){
        return orderDao.getAllOrder();
    }

    public LiveData<List<Order>> getOrderByCustomerName(String name){
        return orderDao.getOrderByCustomerName(name);
    }

    public LiveData<List<DetailOrder>> getAllDetailOrderByCustomer(long customerId){
        return orderDao.getAllDetailOrderByCustomer(customerId);
    }

    public LiveData<List<DetailOrder>> searchDetailOrderByCustomer(long customerId, String productName){
        return orderDao.searchDetailOrderByCustomer(customerId,productName);
    }

    public LiveData<List<OrderCount>> getOrderCountByDate(String startDate, String endDate){
        return orderDao.getOrderCountByDate(startDate,endDate);
    }
}
