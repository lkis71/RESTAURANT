package com.restaurant.controller.dto;

import com.restaurant.entity.FileEntity;
import com.restaurant.entity.Member;
import com.restaurant.entity.Restaurant;
import com.restaurant.entity.RestaurantType;
import com.restaurant.entity.common.Address;
import com.restaurant.entity.common.IntroContent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
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
}
