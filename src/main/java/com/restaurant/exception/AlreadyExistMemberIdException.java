package com.restaurant.exception;

import java.sql.SQLException;

public class AlreadyExistMemberIdException extends SQLException {

    private String messgae;

    public AlreadyExistMemberIdException(String message) {
        this.messgae = message;
    }
}
