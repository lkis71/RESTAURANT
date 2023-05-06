package com.restaurant.controller.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.restaurant.entity.Restaurant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
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
