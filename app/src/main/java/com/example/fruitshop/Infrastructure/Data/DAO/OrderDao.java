package com.example.fruitshop.Infrastructure.Data.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.fruitshop.Application.Model.OrderCount;
import com.example.fruitshop.Domain.Entities.DetailOrder;
import com.example.fruitshop.Domain.Entities.Order;

import java.util.List;

@Dao
public interface OrderDao {
    @Insert
    long add(Order order);
    @Insert
    void addDetails(List<DetailOrder> detailOrders);
    @Delete
    void delete(Order order);
    @Transaction
    default void addOrder(Order order, List<DetailOrder> detailOrders){
        long orderId = add(order);
        detailOrders.forEach(detailOrder -> {
            detailOrder.setOrderId(orderId);
        });

        addDetails(detailOrders);
    }
    @Query("SELECT * FROM orders WHERE id = :id")
    LiveData<Order> getById(long id);
    @Query("SELECT * FROM orders")
    LiveData<List<Order>> getAllOrder();
    @Query("SELECT * FROM orders INNER JOIN users ON orders.userId = users.id WHERE users.name LIKE '%' || :name || '%'")
    LiveData<List<Order>> getOrderByCustomerName(String name);
    @Query("SELECT * FROM detail_orders INNER JOIN orders ON detail_orders.orderId = orders.id WHERE orders.userId = :customerId")
    LiveData<List<DetailOrder>> getAllDetailOrderByCustomer(long customerId);
    @Query("SELECT * FROM detail_orders " +
            "INNER JOIN orders ON detail_orders.orderId = orders.id " +
            "INNER JOIN products ON detail_orders.productId = products.id " +
            "WHERE orders.userId = :customerId AND products.name LIKE '%' || :productName || '%'")
    LiveData<List<DetailOrder>> searchDetailOrderByCustomer(long customerId, String productName);
    @Query("SELECT orderDate, COUNT(*) as orderCount FROM orders WHERE orderDate BETWEEN :startDate AND :endDate GROUP BY orderDate ORDER BY orderDate ASC")
    LiveData<List<OrderCount>> getOrderCountByDate(String startDate, String endDate);
}
