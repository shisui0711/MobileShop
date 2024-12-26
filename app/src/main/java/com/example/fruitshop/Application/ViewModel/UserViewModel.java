package com.example.fruitshop.Application.ViewModel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fruitshop.Application.Model.Result;
import com.example.fruitshop.Domain.Entities.User;
import com.example.fruitshop.Infrastructure.Data.AppDatabase;
import com.example.fruitshop.Infrastructure.Data.DAO.UserDao;

import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserViewModel extends AndroidViewModel {
    private UserDao userDao;

    public UserViewModel(Application application) {
        super(application);
        userDao = AppDatabase.getDatabase(application).userDao();
    }

    public LiveData<User> signIn(String email, String password, LifecycleOwner lifecycleOwner) {
        MutableLiveData<User> resultLiveData = new MutableLiveData<>();

        userDao.getByEmail(email).observe(lifecycleOwner, user -> {
            if (user != null && BCrypt.checkpw(password, user.getPasswordHash())) {
                // Authentication successful, post the user object
                resultLiveData.postValue(user);
            } else {
                // Authentication failed, post null
                resultLiveData.postValue(null);
            }
        });

        return resultLiveData;
    }

    public LiveData<Result<User>> signUp(String name, String email, String password, LifecycleOwner lifecycleOwner){
        MutableLiveData<Result<User>> resultLiveData = new MutableLiveData<>();

        userDao.getByEmail(email).observe(lifecycleOwner,user -> {
            Result<User> result = new Result<>(null,new ArrayList<>());
            result.setLoading(true);
            if(user != null){
                result.getErrors().add("Email đã tồn tại");
                result.setLoading(false);
                resultLiveData.postValue(result);
            }
            else{
                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    User newUser = new User();
                    newUser.setName(name);
                    newUser.setEmail(email);
                    String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
                    newUser.setPasswordHash(passwordHash);

                    // Thêm vào cơ sở dữ liệu
                    userDao.add(newUser);
                    result.setData(newUser);
                    result.setLoading(false);
                    // Trả kết quả về LiveData
                    resultLiveData.postValue(result);
                });
            }
        });
        return resultLiveData;
    }

}
