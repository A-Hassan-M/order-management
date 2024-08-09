package com.qeema.practicaltest.ordermanagement.domain.users.delegates;

import com.qeema.practicaltest.ordermanagement.application.controllers.AuthDelegateImpl;
import com.qeema.practicaltest.ordermanagement.openapi.model.LoginResponse;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.qeema.practicaltest.ordermanagement.openapi.model.Credentials;
import com.qeema.practicaltest.ordermanagement.domain.users.services.AuthService;
import com.qeema.practicaltest.ordermanagement.domain.exceptions.AuthenticationFailedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

@ExtendWith(MockitoExtension.class)
public class AuthDelegateImplTest {
    @Mock
    private AuthService authService;
    @InjectMocks
    private AuthDelegateImpl authDelegate;


    @Test
    public void testAuthenticateWrongCredentials() throws AuthenticationFailedException {
        Credentials credentials = new Credentials("test", "password");
        when(authService.authenticate(credentials))
                .thenThrow(new AuthenticationFailedException("No user found with this email: %s".formatted("test"), "test"));
        AuthenticationFailedException exception = assertThrows(AuthenticationFailedException.class,
                () -> authDelegate.authenticate(credentials));
        assertEquals(exception.getMessage(), "No user found with this email: %s".formatted("test"));
    }

    @Test
    public void testAuthenticateCorrectCredentials() throws AuthenticationFailedException {
        Credentials credentials = new Credentials("test", "P@$$w0rd");
        when(authService.authenticate(credentials)).thenReturn("AccessToken");
        ResponseEntity<LoginResponse> response = authDelegate.authenticate(credentials);
        String accessToken = response.getBody().getAccessToken();
        assertEquals("AccessToken", accessToken);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
