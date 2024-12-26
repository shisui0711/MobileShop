package com.example.fruitshop.Infrastructure.Validation;

import java.net.MalformedURLException;
import java.net.URL;

public class ValidationHelper {
    public static boolean isValidURL(String url) {
        try {
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }
}
