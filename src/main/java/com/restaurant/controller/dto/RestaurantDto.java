package com.restaurant.controller.dto;

import com.restaurant.entity.Member;
import com.restaurant.entity.Restaurant;
import com.restaurant.entity.common.Content;
import com.restaurant.entity.type.RestaurantType;
import com.restaurant.entity.common.Address;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class RestaurantDto {
    
    private Long id;
    private String restaurantName;
    private String zipcode;
    private String streetName;
    private String detailAddress;
    private String contact;
    private RestaurantType restaurantType;
    private String simpleContents;
    private String detailContents;
    private List<MultipartFile> files = new ArrayList<>();
    private String memberId;

    @Builder
    public RestaurantDto(String restaurantName, String zipcode, String streetName, String detailAddress, String contact, RestaurantType restaurantType, String simpleContents, String detailContents, List<MultipartFile> files, String memberId) {
        this.restaurantName = restaurantName;
        this.zipcode = zipcode;
        this.streetName = streetName;
        this.detailAddress = detailAddress;
        this.contact = contact;
        this.restaurantType = restaurantType;
        this.simpleContents = simpleContents;
        this.detailContents = detailContents;
        this.files = files;
        this.memberId = memberId;
    }

    public Restaurant toEntity() {

        Address address = new Address(zipcode, streetName, detailAddress);
        Content introContent = new Content(simpleContents, detailContents);

        return Restaurant.builder()
                .restaurantName(restaurantName)
                .address(address)
                .contact(contact)
                .restaurantType(restaurantType)
                .content(introContent)
                .build();
    }
}
