package com.qeema.practicaltest.ordermanagement.application.mappers;

import com.qeema.practicaltest.ordermanagement.domain.order.entities.Order;
import com.qeema.practicaltest.ordermanagement.domain.order.entities.ValidationError;
import com.qeema.practicaltest.ordermanagement.openapi.model.OrderDto;
import com.qeema.practicaltest.ordermanagement.openapi.model.OrderResponse;
import com.qeema.practicaltest.ordermanagement.openapi.model.ValidationErrorDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface OrderMapper {

    @Mapping(target = "orderItems", source = "products")
    Order mapToEntity(OrderDto order);

    @Mapping(target = "errorMessage", source = "errorMessage")
    ValidationErrorDto mapValidationToDto(ValidationError error);

    @Mapping(target = "orderId", source = "id")
    @Mapping(target = "items", source = "orderItems")
    OrderResponse mapToResponse(Order order);
}
