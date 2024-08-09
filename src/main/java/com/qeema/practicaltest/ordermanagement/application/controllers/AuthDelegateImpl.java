package com.qeema.practicaltest.ordermanagement.application.controllers;

import lombok.SneakyThrows;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import com.qeema.practicaltest.ordermanagement.openapi.model.Credentials;
import com.qeema.practicaltest.ordermanagement.openapi.model.LoginResponse;
import com.qeema.practicaltest.ordermanagement.openapi.api.AuthApiDelegate;
import com.qeema.practicaltest.ordermanagement.domain.users.services.AuthService;

@Service
@RequiredArgsConstructor
public class AuthDelegateImpl implements AuthApiDelegate {

    private final AuthService authService;

    @Override
    @SneakyThrows
    public ResponseEntity<LoginResponse> authenticate(Credentials credentials) {
        String accessToken = authService.authenticate(credentials);
        return ResponseEntity.ok(new LoginResponse().message("Authenticated").accessToken(accessToken));
    }
}
