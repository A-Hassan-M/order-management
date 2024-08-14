package com.qeema.practicaltest.ordermanagement.application.controllers;

import java.util.List;

import lombok.SneakyThrows;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import com.qeema.practicaltest.ordermanagement.openapi.model.OrderDto;
import com.qeema.practicaltest.ordermanagement.openapi.model.OrderResponse;
import com.qeema.practicaltest.ordermanagement.openapi.api.OrdersApiDelegate;
import com.qeema.practicaltest.ordermanagement.application.mappers.OrderMapper;
import com.qeema.practicaltest.ordermanagement.domain.order.services.OrderService;
import com.qeema.practicaltest.ordermanagement.openapi.model.OrderCreatedResponse;

@Service
@RequiredArgsConstructor
public class OrderDelegateImpl implements OrdersApiDelegate {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @SneakyThrows
    @Override
    public ResponseEntity<OrderCreatedResponse> createOrder(OrderDto order) {
        OrderCreatedResponse createdOrderResponse = orderService.createOrder(orderMapper.mapToEntity(order));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderResponse);
    }

    @Override
    public ResponseEntity<List<OrderResponse>> listOrders() {
        List<OrderResponse> orderList = orderService.listOrders().stream().map(orderMapper::mapToResponse).toList();
        return ResponseEntity.ok(orderList);
    }
}
