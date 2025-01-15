package com.example.fruitshop.Infrastructure.Tool;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class ValidationHelper {
    public static boolean hasScheme(String uri) {
        try {
            URI parsedURI = new URI(uri);
            return parsedURI.getScheme() != null; // Kiểm tra xem scheme có khác null không
        } catch (URISyntaxException e) {
            return false; // Nếu có ngoại lệ, chuỗi không phải là một URI hợp lệ
        }
    }
}
