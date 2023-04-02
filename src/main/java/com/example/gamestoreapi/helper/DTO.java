package com.example.gamestoreapi.helper;

import lombok.Data;

@Data
public class DTO<T> {
    private Integer status;
    private String message;
    private T object;

    public DTO(Integer status, String message, T object) {
        this.status = status;
        this.message = message;
        this.object = object;
    }
}
