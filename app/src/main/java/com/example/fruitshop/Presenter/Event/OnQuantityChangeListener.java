package com.example.fruitshop.Presenter.Event;

import com.example.fruitshop.Application.Model.ProductCart;

public interface OnQuantityChangeListener {
    void onQuantityChanged(ProductCart productCart);
}
