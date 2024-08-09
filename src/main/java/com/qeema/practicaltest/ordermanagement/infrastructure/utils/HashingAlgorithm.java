package com.qeema.practicaltest.ordermanagement.infrastructure.utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

public class HashingAlgorithm {

    private static final PasswordEncoder passwordEncoder =
            PasswordEncoderFactories.createDelegatingPasswordEncoder();
    public static String hash(String password){
        return passwordEncoder.encode(password);
    }
    public static boolean matches(String password, String hashedPassword){
        return passwordEncoder.matches(password, hashedPassword);
    }

    public static void main(String[] args) {
        System.out.println(hash("admin123456"));
    }
}
