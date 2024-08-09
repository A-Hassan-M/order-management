package com.qeema.practicaltest.ordermanagement.domain.order.services;

import java.util.List;

import com.qeema.practicaltest.ordermanagement.domain.order.repository.ValidationErrorRepository;
import com.qeema.practicaltest.ordermanagement.domain.product.entities.Product;
import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.scheduling.annotation.Async;
import com.qeema.practicaltest.ordermanagement.domain.order.entities.Order;
import com.qeema.practicaltest.ordermanagement.infrastructure.utils.OrderUtils;
import com.qeema.practicaltest.ordermanagement.domain.exceptions.OrderException;
import com.qeema.practicaltest.ordermanagement.domain.order.entities.OrderStatus;
import com.qeema.practicaltest.ordermanagement.openapi.model.OrderCreatedResponse;
import com.qeema.practicaltest.ordermanagement.domain.order.entities.OrderProduct;
import com.qeema.practicaltest.ordermanagement.domain.order.entities.ValidationError;
import com.qeema.practicaltest.ordermanagement.domain.product.service.ProductService;
import com.qeema.practicaltest.ordermanagement.domain.order.validation.OrderValidator;
import com.qeema.practicaltest.ordermanagement.domain.order.repository.OrderRepository;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderValidator orderValidator;
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final ValidationErrorRepository errorRepository;

    public OrderCreatedResponse createOrder(Order order) throws OrderException {
        List<ValidationError> errors = orderValidator.validate(order);
        if(!CollectionUtils.isEmpty(errors)){
            throw new OrderException(errors);
        }
        Order orderPlaceholder = saveInitialOrder(order);
        fulfillOrder(orderPlaceholder);
        return new OrderCreatedResponse().orderId(orderPlaceholder.getId()).message("Order is now processing");
    }

    @Async
    @Transactional
    protected void fulfillOrder(Order order) {
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            order.setOrderItems(assignProductsData(order, order.getOrderItems()));
            List<ValidationError> errors = orderValidator.validateAsync(order);
            if (CollectionUtils.isEmpty(errors)) {
                order.setStatus(OrderStatus.SUCCESS);
                productService.reserveProducts(order.getOrderItems());
                order.calculateTotalPrice();
                orderRepository.save(order);
                return;
            }
            Order savedOrder = orderRepository.findById(order.getId()).orElse(order);
            savedOrder.setOrderItems(order.getOrderItems());
            savedOrder.setStatus(OrderStatus.FAILED);
            orderRepository.save(savedOrder);
            errorRepository.saveAll(errors);
        });
    }

    private List<OrderProduct> assignProductsData(Order order, List<OrderProduct> orderItems) {
        List<Product> products = productService.getProducts(orderItems);
        Map<String, Product> productMap = products.stream().collect(Collectors.toMap(Product::getName, Function.identity()));
        orderItems.forEach(item -> {
            Product product = productMap.get(item.getProductName());
            if(product != null) {
                item.setProductData(product);
            }
            item.setOrder(order);
        });
        return orderItems;
    }

    private Order saveInitialOrder(Order order) {
        String orderId = OrderUtils.generateOrderId();
        order.setStatus(OrderStatus.PENDING);
        order.setId(orderId);
        List<OrderProduct> orderItems = order.getOrderItems();
        order.setOrderItems(null);
        orderRepository.save(order);
        order.setOrderItems(orderItems);
        return order;
    }

    public List<Order> listOrders() {
        return orderRepository.findAll();
    }
}
