package com.qeema.practicaltest.ordermanagement.domain.users.entities;

import lombok.Data;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Data
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    private String code;
    @Column(name = "_name")
    private String name;
}
