package com.qeema.practicaltest.ordermanagement.domain.order.validation;

import java.util.List;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import com.qeema.practicaltest.ordermanagement.domain.order.entities.Order;
import com.qeema.practicaltest.ordermanagement.domain.product.entities.Product;
import com.qeema.practicaltest.ordermanagement.domain.order.entities.OrderProduct;
import com.qeema.practicaltest.ordermanagement.domain.order.entities.ValidationError;
import com.qeema.practicaltest.ordermanagement.domain.product.service.ProductService;

@RequiredArgsConstructor
public class StockValidation implements ValidationStrategy{

    private final ProductService productService;
    @Override
    public List<ValidationError> validate(Order order) {
        List<OrderProduct> items = order.getOrderItems();
        List<ValidationError> errors = new ArrayList<>();
        for (OrderProduct item : items) {
            Product product = item.getProduct();
            if(product == null){
                errors.add(new ValidationError(order, "Product: %s doesn't exist in stock.".formatted(item.getProductName())));
            } else if (product.getQuantity() < item.getQuantity()) {
                errors.add(new ValidationError(order, "Product: %s only has %d in stock.".formatted(item.getProductName(), product.getQuantity())));
            }
        }
        return errors;
    }
}
