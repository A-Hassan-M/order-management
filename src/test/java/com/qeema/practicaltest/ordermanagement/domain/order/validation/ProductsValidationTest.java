package com.qeema.practicaltest.ordermanagement.domain.order.validation;

import java.util.List;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import com.qeema.practicaltest.ordermanagement.domain.order.entities.Order;
import com.qeema.practicaltest.ordermanagement.domain.order.entities.OrderProduct;
import com.qeema.practicaltest.ordermanagement.domain.order.entities.ValidationError;

class ProductsValidationTest {

    @InjectMocks
    private ProductsValidation productsValidation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidateProductSuccess() {
        List<OrderProduct> orderItems = List.of(new OrderProduct(1L, "item 1", 2, 10.0, 20.0),
                new OrderProduct(2L, "item 2", 2, 10.0, 20.0));

        Order order = new Order("order-1", orderItems);
        List<ValidationError> errors = productsValidation.validate(order);
        assertTrue(errors.isEmpty());
    }

    @Test
    void testValidateProductFailure() {
        List<OrderProduct> orderItems = List.of(new OrderProduct(1L, "item 1", 2, 10.0, 20.0),
                new OrderProduct(1L, "item 1", 2, 10.0, 20.0));

        Order order = new Order("order-1", orderItems);
        List<ValidationError> errors = productsValidation.validate(order);
        assertFalse(errors.isEmpty());
        String errorMessage = errors.get(0).getErrorMessage();
        assertEquals("Product: item 1 is added twice", errorMessage);
    }
}
