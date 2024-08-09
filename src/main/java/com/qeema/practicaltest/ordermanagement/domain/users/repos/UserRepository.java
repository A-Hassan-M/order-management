package com.qeema.practicaltest.ordermanagement.domain.users.repos;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.qeema.practicaltest.ordermanagement.domain.users.entities.User;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
