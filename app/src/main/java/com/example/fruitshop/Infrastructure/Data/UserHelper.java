package com.example.fruitshop.Infrastructure.Data;

import android.content.Context;

import com.example.fruitshop.Domain.Entities.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class UserHelper {
    private Context context;
    private TinyDB tinyDB;
    private Gson gson;

    public UserHelper(Context context) {
        this.context = context;
        tinyDB = new TinyDB(context);
        gson = new Gson();
    }

    public void saveUser(User user){
        tinyDB.putString("user",gson.toJson(user));
    }

    public User getUserSigned(){
        String json = tinyDB.getString("user");
        if(json.equals("")) return null;
        Type type = new TypeToken<User>() {}.getType();
        return  gson.fromJson(json,type);
    }
    public void removeUser(){
        tinyDB.remove("user");
    }
}
