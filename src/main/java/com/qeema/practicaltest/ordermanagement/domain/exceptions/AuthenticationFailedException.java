package com.qeema.practicaltest.ordermanagement.domain.exceptions;

import lombok.Getter;

@Getter
public class AuthenticationFailedException extends Exception{
    private final String email;

    public AuthenticationFailedException(String message, String email) {
        super(message);
        this.email = email;
    }
}
