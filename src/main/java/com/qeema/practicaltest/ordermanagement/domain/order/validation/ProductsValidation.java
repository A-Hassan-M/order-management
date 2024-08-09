package com.qeema.practicaltest.ordermanagement.domain.order.validation;

import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;
import com.qeema.practicaltest.ordermanagement.domain.order.entities.Order;
import com.qeema.practicaltest.ordermanagement.domain.order.entities.OrderProduct;
import com.qeema.practicaltest.ordermanagement.domain.order.entities.ValidationError;

public class ProductsValidation implements ValidationStrategy{
    @Override
    public List<ValidationError> validate(Order order) {
        Set<String> productNames = new HashSet<>();
        List<ValidationError> errors = new ArrayList<>();
        for (OrderProduct product : order.getOrderItems()) {
            if (!productNames.add(product.getProductName())) {
                errors.add(new ValidationError(order, "Product: %s is added twice".formatted(product.getProductName())));
            }
        }
        return errors;
    }
}
