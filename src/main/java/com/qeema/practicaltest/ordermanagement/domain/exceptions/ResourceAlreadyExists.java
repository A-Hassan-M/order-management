package com.qeema.practicaltest.ordermanagement.domain.exceptions;

import lombok.Getter;

@Getter
public class ResourceAlreadyExists extends Exception {
    private final String key;
    private final Object value;

    public ResourceAlreadyExists(String resource, String key, Object value) {
        super("%s already exists with this %s: %s".formatted(resource, key, value));
        this.key = key;
        this.value = value;
    }
}
