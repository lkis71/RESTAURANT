package com.restaurant.exception;

import com.sun.net.httpserver.HttpsServer;
import org.springframework.http.HttpStatus;

public class UserJoinException extends RuntimeException {

    private ErrorCode errorCode;

    public UserJoinException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
