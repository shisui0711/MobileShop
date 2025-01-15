package com.example.fruitshop.Infrastructure.Tool;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

public class Extension {
    public static <T> void observeOnce(LiveData<T> liveData, LifecycleOwner lifecycleOwner, Observer<T> observer) {
        liveData.observe(lifecycleOwner, new Observer<T>() {
            @Override
            public void onChanged(T t) {
                observer.onChanged(t);
                liveData.removeObserver(this);
            }
        });
    }
}
