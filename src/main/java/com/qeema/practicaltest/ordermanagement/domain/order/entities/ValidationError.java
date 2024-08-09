package com.qeema.practicaltest.ordermanagement.domain.order.entities;

import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "validation_error")
@NoArgsConstructor
@AllArgsConstructor
public class ValidationError {
    @Id
    @Basic(optional = false)
    @GenericGenerator(name = "generator")
    @GeneratedValue(generator = "generator")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private Order order;
    private String errorMessage;

    public ValidationError(Order order, String errorMessage) {
        this.order = order;
        this.errorMessage = errorMessage;
    }
}
