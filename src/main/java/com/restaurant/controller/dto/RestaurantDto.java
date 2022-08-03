package com.restaurant.controller.dto;

import com.restaurant.entity.FileEntity;
import com.restaurant.entity.common.Address;
import com.restaurant.entity.common.IntroContent;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestaurantDto {
    
    private String restaurantNm;
    private Address address;
    private String contact;
    private String category;
    private IntroContent content;
    private FileEntity file;
}
