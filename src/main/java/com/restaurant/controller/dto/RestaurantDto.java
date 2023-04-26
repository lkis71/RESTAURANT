package com.restaurant.controller.dto;

import com.restaurant.entity.FileEntity;
import com.restaurant.entity.Member;
import com.restaurant.entity.Restaurant;
import com.restaurant.entity.RestaurantType;
import com.restaurant.entity.common.Address;
import com.restaurant.entity.common.IntroContent;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
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
    private MultipartFile file;
    private Member member;

    public Restaurant toEntity() {

        Address address = new Address(zipcode, streetName, detailAddress);
        IntroContent introContent = new IntroContent(simpleContents, detailContents);

        return Restaurant.builder()
                .restaurantName(restaurantName)
                .address(address)
                .contact(contact)
                .restaurantType(restaurantType)
                .content(introContent)
                .member(member)
                .build();
    }

    @Builder
    public RestaurantDto(String restaurantName, String zipcode, String streetName, String detailAddress, String contact, RestaurantType restaurantType, String simpleContents, String detailContents, MultipartFile file, Member member) {
        this.restaurantName = restaurantName;
        this.zipcode = zipcode;
        this.streetName = streetName;
        this.detailAddress = detailAddress;
        this.contact = contact;
        this.restaurantType = restaurantType;
        this.simpleContents = simpleContents;
        this.detailContents = detailContents;
        this.file = file;
        this.member = member;
    }
}
