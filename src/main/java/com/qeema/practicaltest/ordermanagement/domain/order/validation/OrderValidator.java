package com.qeema.practicaltest.ordermanagement.domain.order.validation;

import java.util.List;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import com.qeema.practicaltest.ordermanagement.domain.order.entities.Order;
import com.qeema.practicaltest.ordermanagement.domain.order.entities.ValidationError;

@RequiredArgsConstructor
public class OrderValidator {
    private final List<ValidationStrategy> syncValidations;
    private final List<ValidationStrategy> asyncValidations;

    public List<ValidationError> validate(Order order) {
        return syncValidations.stream().map(validation -> validation.validate(order)).flatMap(Collection::stream).toList();
    }

    public List<ValidationError> validateAsync(Order order) {
        return asyncValidations.stream().map(validation -> validation.validate(order)).flatMap(Collection::stream).toList();
    }
}