package com.restaurant.controller.response;

import com.querydsl.core.annotations.QueryProjection;
import com.restaurant.entity.Restaurant;
import com.restaurant.entity.RestaurantFile;
import com.restaurant.entity.common.Content;
import com.restaurant.entity.type.RestaurantType;
import com.restaurant.entity.common.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RestaurantResponse {

    private Long id;
    private String restaurantName;
    private Address address;
    private String contact;
    private Content content;
    private RestaurantType restaurantType;

    private Long fileId;
    private String fileName;

    @QueryProjection
    public RestaurantResponse(Long id, String restaurantName, Address address, String contact,
                              Content content, RestaurantType restaurantType, Long fileId, String fileName) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.address = address;
        this.contact = contact;
        this.content = content;
        this.restaurantType = restaurantType;
        this.fileId = fileId;
        this.fileName = fileName;
    }

    public RestaurantResponse(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.restaurantName = restaurant.getRestaurantName();
        this.address = restaurant.getAddress();
        this.contact = restaurant.getContact();
        this.content = restaurant.getContent();
        this.restaurantType = restaurant.getRestaurantType();

        if (restaurant.getRestaurantFile() != null) {
            this.fileId = restaurant.getRestaurantFile().getFileMaster().getId();
            this.fileName = restaurant.getRestaurantFile().getFileMaster().getFileName();
        }
    }
}
