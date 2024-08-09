package com.qeema.practicaltest.ordermanagement.domain.exceptions;

import lombok.Getter;
import java.util.List;
import com.qeema.practicaltest.ordermanagement.domain.order.entities.ValidationError;

@Getter
public class OrderException extends Exception{
    private final List<ValidationError> errors;

    public OrderException(List<ValidationError> errors) {
        super("Failed to create your order, please check your order items and try again");
        this.errors = errors;
    }
}
