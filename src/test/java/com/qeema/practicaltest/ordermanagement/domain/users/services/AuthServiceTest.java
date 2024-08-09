package com.qeema.practicaltest.ordermanagement.domain.users.services;

import java.util.Set;

import com.qeema.practicaltest.ordermanagement.domain.users.entities.User;
import com.qeema.practicaltest.ordermanagement.domain.exceptions.AuthenticationFailedException;
import com.qeema.practicaltest.ordermanagement.openapi.model.Credentials;
import com.qeema.practicaltest.ordermanagement.infrastructure.utils.HashingAlgorithm;
import com.qeema.practicaltest.ordermanagement.infrastructure.utils.JwtService;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private JwtService jwtService;
    @InjectMocks
    private AuthService authService;

    @Test
    public void testAuthenticateWrongCredentials(){
        when(userService.findByEmail(any()))
                .thenThrow(new UsernameNotFoundException("No user found with this email: %s".formatted("test")));
        AuthenticationFailedException exception = assertThrows(AuthenticationFailedException.class,
                () -> authService.authenticate(new Credentials("test", "password")));
        assertEquals(exception.getMessage(), "No user found with this email: %s".formatted("test"));
    }

    @Test
    public void testAuthenticateWrongCredentials2() {
        User user = new User(1L, "test", "test", HashingAlgorithm.hash("password"), Set.of());
        Credentials credentials = new Credentials("test", "P@$$w0rd");
        when(userService.findByEmail(any())).thenReturn(user);
        AuthenticationFailedException exception = assertThrows(AuthenticationFailedException.class,
                () -> authService.authenticate(credentials));
        assertEquals(exception.getMessage(), "Invalid credentials");
    }

    @Test
    public void testAuthenticateCorrectCredentials() throws AuthenticationFailedException {
        User user = new User(1L, "test", "test", HashingAlgorithm.hash("P@$$w0rd"), Set.of());
        Credentials credentials = new Credentials("test", "P@$$w0rd");
        when(userService.findByEmail(any())).thenReturn(user);
        when(jwtService.generateToken(any())).thenReturn("AccessToken");
        String token = authService.authenticate(credentials);
        verify(jwtService, times(1)).generateToken(any());
        assertEquals("AccessToken", token);
    }
}
