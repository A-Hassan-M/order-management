package com.qeema.practicaltest.ordermanagement.application.controllers;

import com.qeema.practicaltest.ordermanagement.application.mappers.ProductMapper;
import com.qeema.practicaltest.ordermanagement.domain.product.entities.Product;
import com.qeema.practicaltest.ordermanagement.domain.product.service.ProductService;
import com.qeema.practicaltest.ordermanagement.infrastructure.utils.SecurityRoles;
import com.qeema.practicaltest.ordermanagement.openapi.api.ProductsApiDelegate;
import com.qeema.practicaltest.ordermanagement.openapi.model.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductDelegateImpl implements ProductsApiDelegate {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @SneakyThrows
    @Override
    @PreAuthorize("hasPermission(null, '" + SecurityRoles.ADMIN + "')")
    public ResponseEntity<ProductDto> updateProduct(Long productId, ProductDto product) {
        Product savedProduct = productService.updateProduct(productId, productMapper.mapToEntity(product));
        return ResponseEntity.ok(productMapper.mapToProductDto(savedProduct));
    }

    @SneakyThrows
    @Override
    @PreAuthorize("hasPermission(null, '" + SecurityRoles.ADMIN + "')")
    public ResponseEntity<ProductDto> addProduct(ProductDto productDTO) {
        Product product = productMapper.mapToEntity(productDTO);
        Product savedProduct = productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productMapper.mapToProductDto(savedProduct));
    }

    @Override
    @PreAuthorize("hasPermission(null, '" + SecurityRoles.ADMIN + "')")
    public ResponseEntity<Void> deleteProduct(Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @SneakyThrows
    @Override
    public ResponseEntity<ProductDto> getProduct(Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(productMapper.mapToProductDto(product));
    }

    @Override
    public ResponseEntity<List<ProductDto>> listProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products.stream().map(productMapper::mapToProductDto).toList());
    }
}
