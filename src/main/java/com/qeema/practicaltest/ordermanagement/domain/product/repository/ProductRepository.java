package com.qeema.practicaltest.ordermanagement.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.qeema.practicaltest.ordermanagement.domain.product.entities.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameIn(List<String> productNames);

    boolean existsByName(String name);
}
