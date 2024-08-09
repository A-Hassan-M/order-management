package com.qeema.practicaltest.ordermanagement.domain.users.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.qeema.practicaltest.ordermanagement.openapi.model.Credentials;
import com.qeema.practicaltest.ordermanagement.domain.users.entities.User;
import com.qeema.practicaltest.ordermanagement.infrastructure.utils.JwtService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.qeema.practicaltest.ordermanagement.infrastructure.utils.HashingAlgorithm;
import com.qeema.practicaltest.ordermanagement.domain.exceptions.AuthenticationFailedException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;

    public String authenticate(Credentials credentials) throws AuthenticationFailedException {
        try {
            User user = userService.findByEmail(credentials.getEmail());
            if (!HashingAlgorithm.matches(credentials.getPassword(), user.getPassword())) {
                throw new AuthenticationFailedException("Invalid credentials", credentials.getEmail());
            }
            return jwtService.generateToken(user);
        }catch (UsernameNotFoundException ex){
            throw new AuthenticationFailedException(ex.getMessage(), credentials.getEmail());
        }
    }
}
