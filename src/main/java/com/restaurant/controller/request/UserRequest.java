package com.restaurant.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRequest {
    
    private String hmpgId;
    private String password;
    private String userNm;
    private String phoneNum;
    private String zipcode;
    private String streetNm;
    private String detailAddress;
    private String registNum;
    private String userType;
}
