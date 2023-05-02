package com.restaurant.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class LoginDto {

    private String memberId;
    private String password;
}
