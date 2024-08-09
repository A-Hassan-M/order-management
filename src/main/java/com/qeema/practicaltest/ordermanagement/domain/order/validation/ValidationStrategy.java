package com.qeema.practicaltest.ordermanagement.domain.order.validation;

import java.util.List;
import com.qeema.practicaltest.ordermanagement.domain.order.entities.Order;
import com.qeema.practicaltest.ordermanagement.domain.order.entities.ValidationError;

public interface ValidationStrategy {
    List<ValidationError> validate(Order order);
}