package com.restaurant.controller.response;

import com.restaurant.entity.Restaurant;
import com.restaurant.entity.RestaurantImage;
import com.restaurant.entity.common.Content;
import com.restaurant.entity.type.RestaurantType;
import com.restaurant.entity.common.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class RestaurantResponse {

    private Long id;
    private String restaurantName;
    private Address address;
    private String contact;
    private RestaurantType restaurantType;
    private Content content;
    private List<RestaurantImage> restaurantFiles = new ArrayList<>();

    public RestaurantResponse(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.restaurantName = restaurant.getRestaurantName();
        this.address = restaurant.getAddress();
        this.contact = restaurant.getContact();
        this.restaurantType = restaurant.getRestaurantType();
        this.content = restaurant.getContent();
        this.restaurantFiles = restaurant.getRestaurantImages();
    }
}
