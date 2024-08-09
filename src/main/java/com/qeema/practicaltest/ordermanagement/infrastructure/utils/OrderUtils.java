package com.qeema.practicaltest.ordermanagement.infrastructure.utils;

import java.util.UUID;

public class OrderUtils {

    public static String generateOrderId() {
        return UUID.randomUUID().toString();
    }
}
