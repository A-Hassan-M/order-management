package com.qeema.practicaltest.ordermanagement.domain.order.entities;

import lombok.Data;
import java.util.List;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Entity
@Table(name = "_order")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    private String id;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "orderId")
    private List<OrderProduct> orderItems;
    private OrderStatus status;
    private Double totalPrice;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "orderId")
    private List<ValidationError> errors;

    public void calculateTotalPrice() {
        totalPrice = orderItems.stream().mapToDouble(OrderProduct::getSubtotal).sum();
    }
}
