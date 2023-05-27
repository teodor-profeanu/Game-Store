package com.example.gamestoreapi.helper;

import lombok.Data;

/**
 * Data transfer class to act as data for http request responses. Can hold a status, message and an object of type T
 * @param <T> Can be any object you wish to send over a http request
 */
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
