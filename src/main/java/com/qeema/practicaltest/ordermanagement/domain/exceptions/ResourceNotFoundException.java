package com.qeema.practicaltest.ordermanagement.domain.exceptions;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends Exception{
    private final String key;
    private final Object value;

    public ResourceNotFoundException(String resource, String key, Object value) {
        super("No %s exists with this %s: %s".formatted(resource, key, value));
        this.key = key;
        this.value = value;
    }
}
