package com.example.fruitshop.Infrastructure.Tool;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

public class Converter {
    public static <T> CompletableFuture<T> liveDataToCompletableFuture (LiveData<T> liveData) {
        CompletableFuture<T> future = new CompletableFuture<>();
        CountDownLatch latch = new CountDownLatch(1);

        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(T value) {
                future.complete(value);
                latch.countDown();
                // Gỡ bỏ observer sau khi nhận giá trị
                liveData.removeObserver(this);
            }
        };

        liveData.observeForever(observer);

        // Chờ cho đến khi latch được đếm xuống
        CompletableFuture.runAsync(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                future.completeExceptionally(e);
            }
        });

        return future;
    }
}
