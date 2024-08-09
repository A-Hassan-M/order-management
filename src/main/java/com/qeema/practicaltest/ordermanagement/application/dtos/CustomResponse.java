package com.qeema.practicaltest.ordermanagement.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomResponse<T> {

    private String message;
    private T data;

}
