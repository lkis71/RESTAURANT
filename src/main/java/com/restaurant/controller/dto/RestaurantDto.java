package com.restaurant.controller.dto;

import com.restaurant.entity.FileEntity;
import com.restaurant.entity.Restaurant;
import com.restaurant.entity.common.Address;
import com.restaurant.entity.common.IntroContent;

import lombok.Getter;

@Getter
public class RestaurantDto {
    
    private String restaurantNm;
    private Address address;
    private String contact;
    private String category;
    private IntroContent content;
    private FileEntity file;

    public RestaurantDto(Restaurant restaurant) {
        this.restaurantNm = restaurant.getRestaurantNm();
        this.address = restaurant.getAddress();
        this.contact = restaurant.getContact();
        this.category = restaurant.getCategory();
        this.content = restaurant.getContent();
        this.file = restaurant.getFile();
    }
}
