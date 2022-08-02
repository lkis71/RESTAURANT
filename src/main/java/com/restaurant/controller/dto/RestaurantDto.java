package com.restaurant.controller.dto;

import com.restaurant.entity.Restaurant;

import lombok.Getter;

@Getter
public class RestaurantDto {
    
    private Long id;
    private String restaurantNm;
    private String contact;

    public RestaurantDto(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.restaurantNm = restaurant.getRestaurantNm();
        this.contact = restaurant.getContact();
    }
}
