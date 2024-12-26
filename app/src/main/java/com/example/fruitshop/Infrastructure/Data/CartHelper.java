package com.example.fruitshop.Infrastructure.Data;

import android.content.Context;

import com.example.fruitshop.Application.Model.ProductCart;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CartHelper {
    private Context context;
    private TinyDB tinyDB;
    private Gson gson;

    public CartHelper(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
        gson = new Gson();
    }

    public void insertProduct(ProductCart item){
        ArrayList<ProductCart> productCarts = getProductsInCart();
        boolean isExist = false;
        int index = 0;
        for (int i = 0; i < productCarts.size(); i++) {
            if(productCarts.get(i).getId() == item.getId()){
                index = i;
                isExist = true;
                break;
            }
        }

        if(isExist){
            ProductCart exist = productCarts.get(index);
            exist.setQuantity(exist.getQuantity() + item.getQuantity());
        }else {
            productCarts.add(item);
        }
        tinyDB.putString("cart",gson.toJson(productCarts));

    }
    public void updateQuantity(ProductCart productCart, int quantity){
        ArrayList<ProductCart> productCarts = getProductsInCart();
        int index = productCarts.indexOf(productCart);
        if(index != -1){
            productCarts.get(index).setQuantity(quantity);
        }
        tinyDB.putString("cart",gson.toJson(productCarts));
    }
    public void removeProduct(ProductCart item){
        ArrayList<ProductCart> productCarts = getProductsInCart();
        productCarts.remove(item);
        tinyDB.putString("cart",gson.toJson(productCarts));
    }
    public ArrayList<ProductCart> getProductsInCart(){
        String json = tinyDB.getString("cart");
        if(json.equals("")) return new ArrayList<>();
        Type type = new TypeToken<ArrayList<ProductCart>>() {}.getType();
        return gson.fromJson(json,type);
    }
}
