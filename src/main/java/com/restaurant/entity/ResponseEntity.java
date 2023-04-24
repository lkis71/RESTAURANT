package com.restaurant.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class ResponseEntity<T> {

    private boolean success;
    private T body;
    private HttpStatus status;
}
