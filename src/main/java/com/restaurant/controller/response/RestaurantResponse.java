package com.restaurant.controller.response;

import com.querydsl.core.annotations.QueryProjection;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.ListPath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.restaurant.entity.QRestaurantFile;
import com.restaurant.entity.Restaurant;
import com.restaurant.entity.RestaurantFile;
import com.restaurant.entity.common.Content;
import com.restaurant.entity.common.QAddress;
import com.restaurant.entity.common.QContent;
import com.restaurant.entity.type.RestaurantType;
import com.restaurant.entity.common.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class RestaurantResponse {

    private Long id;
    private String restaurantName;
    private Address address;
    private String contact;
    private RestaurantType restaurantType;
    private Content content;
    private List<RestaurantFile> restaurantFiles = new ArrayList<>();

    public RestaurantResponse(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.restaurantName = restaurant.getRestaurantName();
        this.address = restaurant.getAddress();
        this.contact = restaurant.getContact();
        this.restaurantType = restaurant.getRestaurantType();
        this.content = restaurant.getContent();
        this.restaurantFiles = restaurant.getRestaurantFiles();
    }
}
