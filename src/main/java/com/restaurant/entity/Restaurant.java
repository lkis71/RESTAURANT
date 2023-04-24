package com.restaurant.entity;

import javax.persistence.*;

import com.restaurant.entity.common.Address;
import com.restaurant.entity.common.IntroContent;

import lombok.Builder;
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

    private String restaurantName;

    @Embedded
    private Address address;

    private String contact;

    @Enumerated(EnumType.STRING)
    private RestaurantType restaurantType;

    private IntroContent content;

    @JoinColumn(name = "member_seq")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "file_id")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private FileEntity file;

    @Builder
    public Restaurant(String restaurantName, Address address, String contact, RestaurantType restaurantType, IntroContent content, Member member, FileEntity file) {
        this.restaurantName = restaurantName;
        this.address = address;
        this.contact = contact;
        this.restaurantType = restaurantType;
        this.content = content;
        this.member = member;
        this.file = file;
    }

    public void setRestaurant(String restaurantName, String zipcode, String streetNm, String detailAddress, 
            String contact, RestaurantType restaurantType, String simpleContents, String detailContents, Member member) {

        this.restaurantName = restaurantName;
        this.contact = contact;
        this.restaurantType = restaurantType;
        this.member = member;

        Address address = new Address(zipcode, streetNm, detailAddress);
        this.address = address;

        IntroContent content = new IntroContent(simpleContents, detailContents);
        this.content = content;
    }
}
