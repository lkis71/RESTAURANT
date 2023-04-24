package com.restaurant.controller.dto;

import com.restaurant.entity.Restaurant;

import lombok.Getter;

@Getter
public class MyRestaurantDto {
    
    private Long id;
    private String restaurantName;
    private String contact;

    public MyRestaurantDto(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.restaurantName = restaurant.getRestaurantName();
        this.contact = restaurant.getContact();
    }
}
