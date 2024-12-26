package com.example.fruitshop.Infrastructure.Data;

import android.content.Context;

import com.example.fruitshop.Application.Model.ProductCart;
import com.example.fruitshop.Domain.Entities.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SearchHistoryHelper {
    private Context context;
    private TinyDB tinyDB;
    private Gson gson;

    public SearchHistoryHelper(Context context) {
        this.context = context;
        tinyDB = new TinyDB(context);
        gson = new Gson();
    }

    public void insert(String item){
        ArrayList<String> items = getAll();
        if(!items.contains(item)){
            items.add(item);
        }
        tinyDB.putString("search_history",gson.toJson(items));
    }

    public void remove(String item){
        ArrayList<String> items = getAll();
        items.remove(item);
        tinyDB.putString("search_history",gson.toJson(items));
    }

    public ArrayList<String> getAll(){
        String json = tinyDB.getString("search_history");
        if(json.equals("")) return new ArrayList<>();
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json,type);
    }
}
