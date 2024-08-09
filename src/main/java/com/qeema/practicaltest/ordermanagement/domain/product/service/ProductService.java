package com.qeema.practicaltest.ordermanagement.domain.product.service;

import java.util.List;
import java.util.stream.Collectors;

import com.qeema.practicaltest.ordermanagement.domain.exceptions.ResourceAlreadyExists;
import com.qeema.practicaltest.ordermanagement.domain.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.qeema.practicaltest.ordermanagement.domain.product.entities.Product;
import com.qeema.practicaltest.ordermanagement.domain.order.entities.OrderProduct;
import com.qeema.practicaltest.ordermanagement.domain.product.repository.ProductRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product saveProduct(Product product) throws ResourceAlreadyExists {
        if(productRepository.existsByName(product.getName())){
            throw new ResourceAlreadyExists("Product", "name", product.getName());
        }
        return productRepository.save(product);
    }

    public Product updateProduct(Long productId, Product product) throws ResourceNotFoundException {
        Product dbProduct = getProductById(productId);
        dbProduct.updateWith(product);
        return productRepository.save(dbProduct);
    }

    public Product getProductById(Long id) throws ResourceNotFoundException {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getProducts(List<OrderProduct> orderItems){
        List<String> productNames = orderItems.stream().map(OrderProduct::getProductName).collect(Collectors.toList());
        return productRepository.findByNameIn(productNames);
    }

    @Transactional
    public void reserveProducts(List<OrderProduct> orderItems) {
        // Map product IDs to product details
        List<Product> products = orderItems.stream().map(item -> {
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() - item.getQuantity());
            return product;
        }).toList();
        productRepository.saveAll(products);
    }
}
