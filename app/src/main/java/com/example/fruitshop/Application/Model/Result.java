package com.example.fruitshop.Application.Model;

import java.util.Collection;

public class Result<T> {
    private boolean isLoading;
    private boolean isSuccess;
    private boolean isError;

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
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
