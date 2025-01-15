package com.example.fruitshop.Application.Model;

import java.util.Collection;

public class Result<T> {
    private boolean isLoading;

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    private T data;
    private Collection<String> errors;

    public Result(T data, Collection<String> errors) {
        this.data = data;
        this.errors = errors;
    }

    public T getData() {
        return data;
    }

    public Collection<String> getErrors() {
        return errors;
    }

    public void setData(T data) {
        this.data = data;
    }
}
