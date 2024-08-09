package com.qeema.practicaltest.ordermanagement.domain.product.entities;

import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @Basic(optional = false)
    @GenericGenerator(name = "generator")
    @GeneratedValue(generator = "generator")
    private Long id;
    private String name;
    private double price;
    private int quantity;

    public Product(Long id) {
        this.id = id;
    }

    public void updateWith(Product product) {
        name = product.name;
        price = product.price;
        quantity = product.quantity;
    }
}
