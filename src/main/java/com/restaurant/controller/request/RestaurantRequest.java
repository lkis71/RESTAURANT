package com.restaurant.controller.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestaurantRequest {
    
    private String restaurantNm;
    private String zipcode;
    private String streetNm;
    private String detailAddress;
    private String contact;
    private String category;
    private String simpleContents;
    private String detailContents;
    private MultipartFile file;
}
