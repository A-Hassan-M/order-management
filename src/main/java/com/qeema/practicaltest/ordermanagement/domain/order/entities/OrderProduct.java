package com.qeema.practicaltest.ordermanagement.domain.order.entities;

import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import com.qeema.practicaltest.ordermanagement.domain.product.entities.Product;

@Data
@Entity
@Table(name = "order_product")
@NoArgsConstructor
@AllArgsConstructor
public class OrderProduct {
    @Id
    @Basic(optional = false)
    @GenericGenerator(name = "generator")
    @GeneratedValue(generator = "generator")
    private Long id;
    private Long productId;
    private String productName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private Order order;
    private int quantity;
    private Double pricePerUnit;
    private Double subtotal;
    @Transient
    private Product product;

    public OrderProduct(Long productId, String productName, int quantity, Double pricePerUnit, Double subtotal) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.subtotal = subtotal;
    }

    public void setProductData(Product product) {
        this.product = product;
        this.productId = product.getId();
        this.productName = product.getName();
        this.pricePerUnit = product.getPrice();
        if(quantity != 0){
            subtotal = pricePerUnit * quantity;
        }
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                "id=" + id +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", pricePerUnit=" + pricePerUnit +
                ", subtotal=" + subtotal +
                ", product=" + product +
                '}';
    }
}
