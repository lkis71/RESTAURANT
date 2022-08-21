package com.restaurant.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.restaurant.entity.common.Address;
import com.restaurant.entity.common.IntroContent;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Restaurant {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurantId")
    private Long id;

    private String restaurantNm;

    @Embedded
    private Address address;

    private String contact;

    private String category;

    private IntroContent content;

    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToOne(mappedBy = "restaurant", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private FileEntity file;

    //생성메서드
    public static Restaurant createRestaurant(String restaurantNm, String zipcode, String streetNm, String detailAddress, 
            String contact, String category, String simpleContents, String detailContents, User user) {

        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantNm(restaurantNm);
        restaurant.setContact(contact);
        restaurant.setCategory(category);
        
        Address address = new Address(zipcode, streetNm, detailAddress);
        restaurant.setAddress(address);
        
        IntroContent content = new IntroContent(simpleContents, detailContents);
        restaurant.setContent(content);
        
        restaurant.setUser(user);

        return restaurant;
    }

    public void setRestaurant(String restaurantNm, String zipcode, String streetNm, String detailAddress, 
            String contact, String category, String simpleContents, String detailContents, User user) {

        this.restaurantNm = restaurantNm;
        this.contact = contact;
        this.category = category;
        this.user = user;

        Address address = new Address(zipcode, streetNm, detailAddress);
        this.address = address;

        IntroContent content = new IntroContent(simpleContents, detailContents);
        this.content = content;
    }
}
