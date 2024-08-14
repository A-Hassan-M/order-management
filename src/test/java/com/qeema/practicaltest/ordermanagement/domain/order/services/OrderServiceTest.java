package com.qeema.practicaltest.ordermanagement.domain.order.services;

import java.util.*;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import java.util.concurrent.CompletableFuture;
import org.springframework.util.CollectionUtils;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.qeema.practicaltest.ordermanagement.domain.order.entities.Order;
import com.qeema.practicaltest.ordermanagement.domain.product.entities.Product;
import com.qeema.practicaltest.ordermanagement.domain.exceptions.OrderException;
import com.qeema.practicaltest.ordermanagement.domain.order.entities.OrderStatus;
import com.qeema.practicaltest.ordermanagement.domain.order.entities.OrderProduct;
import com.qeema.practicaltest.ordermanagement.openapi.model.OrderCreatedResponse;
import com.qeema.practicaltest.ordermanagement.domain.order.entities.ValidationError;
import com.qeema.practicaltest.ordermanagement.domain.product.service.ProductService;
import com.qeema.practicaltest.ordermanagement.domain.order.validation.OrderValidator;
import com.qeema.practicaltest.ordermanagement.domain.order.repository.OrderRepository;
import com.qeema.practicaltest.ordermanagement.domain.order.repository.ValidationErrorRepository;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderValidator orderValidator;

    @Mock
    private ProductService productService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ValidationErrorRepository errorRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void testCreateOrderSuccess() throws OrderException {
        Order order = new Order(UUID.randomUUID().toString(),
                List.of(new OrderProduct(1L, "Product 1", 2, 10.0, 20.0)));

        when(orderValidator.validate(order)).thenReturn(Collections.emptyList());
        when(orderRepository.save(any())).thenReturn(order);

        OrderCreatedResponse response = orderService.createOrder(order);
        assertNotNull(response);
        assertEquals("Order is now processing", response.getMessage());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testCreateOrderWithPreSaveValidationErrors() {
        Order order = new Order("order-1", List.of(new OrderProduct(1L, "Product 1", 2, 10.0, 20.0)));
        ValidationError error = new ValidationError(order, "Validation error");

        when(orderValidator.validate(order)).thenReturn(List.of(error));

        OrderException exception = assertThrows(OrderException.class, () -> orderService.createOrder(order));

        assertFalse(CollectionUtils.isEmpty(exception.getErrors()));
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void testFulfillOrderSuccess() {
        Order order = new Order("order-1", List.of(new OrderProduct(1L, "Product 1", 2, 10.0, 20.0)));
        Product product = new Product(1L, "Product 1", 10.0, 5);

        when(productService.getProducts(anyList())).thenReturn(List.of(product));
        when(orderValidator.validateAsync(any(Order.class))).thenReturn(Collections.emptyList());

        CompletableFuture<Void> fulfillFuture = orderService.fulfillOrder(order);
        fulfillFuture.join();
        verify(productService, times(1)).reserveProducts(anyList());
        verify(orderRepository, times(1)).save(order);
        assertEquals(OrderStatus.SUCCESS, order.getStatus());
    }

    @Test
    void testFulfillOrderWithValidationErrors() {
        Order order = new Order("order-1", List.of(new OrderProduct(1L, "Product 1", 2, 10.0, 20.0)));
        Product product = new Product(1L, "Product 1", 10.0, 5);
        ValidationError error = new ValidationError(order, "Validation error");

        when(productService.getProducts(anyList())).thenReturn(List.of(product));
        when(orderValidator.validateAsync(any(Order.class))).thenReturn(List.of(error));
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(orderRepository.save(any())).thenReturn(order);
        when(errorRepository.saveAll(anyList())).thenReturn(List.of(error));

        CompletableFuture<Void> fulfillFuture =orderService.fulfillOrder(order);
        fulfillFuture.join();
        verify(productService, never()).reserveProducts(anyList());
        verify(orderRepository, times(1)).save(any());
        verify(errorRepository, times(1)).saveAll(anyList());
        assertEquals(OrderStatus.FAILED, order.getStatus());
    }

    @Test
    void testAssignProductsData() {
        Order order = new Order("order-1", List.of(new OrderProduct(1L, "Product 1", 2, 0.0, 0.0)));
        Product product = new Product(1L, "Product 1", 10.0, 5);

        when(productService.getProducts(anyList())).thenReturn(List.of(product));

        List<OrderProduct> orderItems = orderService.assignProductsData(order, order.getOrderItems());

        assertNotNull(orderItems);
        assertEquals(1, orderItems.size());
        assertEquals(product.getId(), orderItems.get(0).getProductId());
        assertEquals(product.getPrice(), orderItems.get(0).getPricePerUnit());
    }

    @Test
    void testListOrders() {
        Order order1 = new Order("order-1", List.of(new OrderProduct(1L, "Product 1", 2, 10.0, 20.0)));
        Order order2 = new Order("order-2", List.of(new OrderProduct(2L, "Product 2", 3, 15.0, 45.0)));

        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        List<Order> orders = orderService.listOrders();

        assertEquals(2, orders.size());
        verify(orderRepository, times(1)).findAll();
    }
}
