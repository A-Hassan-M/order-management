package com.qeema.practicaltest.ordermanagement.domain.users.services;

import java.util.Set;
import org.mockito.Mock;
import java.util.Optional;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.qeema.practicaltest.ordermanagement.domain.users.entities.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.qeema.practicaltest.ordermanagement.domain.users.repos.UserRepository;
import com.qeema.practicaltest.ordermanagement.infrastructure.utils.HashingAlgorithm;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    public void testLoadUsernameWithWrongUser() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername("test@test.com"));
        assertEquals(exception.getMessage(), "No user found with this email: %s".formatted("test@test.com"));
    }

    @Test
    public void testLoadUsernameWithExistingUser() {
        String email = "test@test.com";
        User user = new User(1L, "test", "test", HashingAlgorithm.hash("password"), Set.of());
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        User returnedUser = userService.findByEmail(email);
        assertEquals(user.getId(), returnedUser.getId());
    }
}
