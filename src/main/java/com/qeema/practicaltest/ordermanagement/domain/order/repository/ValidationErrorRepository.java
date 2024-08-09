package com.qeema.practicaltest.ordermanagement.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.qeema.practicaltest.ordermanagement.domain.order.entities.ValidationError;

public interface ValidationErrorRepository extends JpaRepository<ValidationError, Long> {
}
