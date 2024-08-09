package com.qeema.practicaltest.ordermanagement.application.mappers;

import com.qeema.practicaltest.ordermanagement.openapi.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.qeema.practicaltest.ordermanagement.openapi.model.ProductDto;
import com.qeema.practicaltest.ordermanagement.openapi.model.OrderProductDto;
import com.qeema.practicaltest.ordermanagement.domain.product.entities.Product;
import com.qeema.practicaltest.ordermanagement.domain.order.entities.OrderProduct;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    OrderProduct mapToOrderProduct(OrderProductDto product);

    OrderItem mapToOrderProduct(OrderProduct product);

    ProductDto mapToProductDto(Product product);

    Product mapToEntity(ProductDto productDTO);
}

