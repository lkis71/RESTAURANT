package com.restaurant.controller.response;

import com.restaurant.entity.FileEntity;
import com.restaurant.entity.Restaurant;
import com.restaurant.entity.RestaurantType;
import com.restaurant.entity.common.Address;
import com.restaurant.entity.common.IntroContent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestaurantResponse {

    private Long id;
    private String restaurantName;
    private Address address;
    private String contact;
    private RestaurantType restaurantType;
    private IntroContent content;
    private FileEntity file;

    public RestaurantResponse(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.restaurantName = restaurant.getRestaurantName();
        this.address = restaurant.getAddress();
        this.contact = restaurant.getContact();
        this.restaurantType = restaurant.getRestaurantType();
        this.content = restaurant.getContent();
        this.file = restaurant.getFile();
    }
}
