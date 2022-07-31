package com.restaurant.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.restaurant.entity.common.Address;
import com.restaurant.entity.common.IntroContent;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Restaurant {
    
    @Id @GeneratedValue
    @Column(name = "restaurantId")
    private Long id;

    private String restaurantNm;

    @Embedded
    private Address address;

    private String contact;

    private String category;

    private IntroContent content;

    //생성메서드
    public void createRestaurant(String restaurantNm, String zipcode, String streetNm, String detailAddress, 
            String contact, String category, String simpleContents, String detailContents) {

        this.restaurantNm = restaurantNm;
        this.contact = contact;
        this.category = category;

        Address address = new Address(zipcode, streetNm, detailAddress);
        this.address = address;

        IntroContent content = new IntroContent(simpleContents, detailContents);
        this.content = content;
    }
}
