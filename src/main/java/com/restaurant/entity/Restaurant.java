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
import lombok.Setter;

@Entity
@Getter @Setter
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
    public static Restaurant createRestaurant(String restaurantNm, String zipcode, String streetNm, String detailAddress, 
            String contact, String category, String simpleContents, String detailContents) {

        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantNm(restaurantNm);
        restaurant.setContact(contact);
        restaurant.setCategory(category);

        Address address = new Address(zipcode, streetNm, detailAddress);
        restaurant.setAddress(address);

        IntroContent content = new IntroContent(simpleContents, detailContents);
        restaurant.setContent(content);

        return restaurant;
    }
}
