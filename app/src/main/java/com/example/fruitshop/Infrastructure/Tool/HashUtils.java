package com.example.fruitshop.Infrastructure.Tool;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {

    public static String sha256(String input) {
        try {
            // Tạo instance của SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Chuyển đổi chuỗi input thành mảng byte
            byte[] hash = digest.digest(input.getBytes());

            // Chuyển byte array thành chuỗi hex
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }
}
