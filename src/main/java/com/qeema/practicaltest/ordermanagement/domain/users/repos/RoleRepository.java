package com.qeema.practicaltest.ordermanagement.domain.users.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.qeema.practicaltest.ordermanagement.domain.users.entities.Role;

public interface RoleRepository extends JpaRepository<Role, String> {
}
