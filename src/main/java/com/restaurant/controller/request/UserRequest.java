package com.restaurant.controller.request;

import lombok.Getter;

@Getter
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
