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

    private String restaurantNm;

    @Embedded
    private Address address;

    private String contact;

    private String category;

    private IntroContent content;

    @JoinColumn(name = "userSeq")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member user;

    @OneToOne(mappedBy = "restaurant", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private FileEntity file;

    @Builder
    public Restaurant(String restaurantNm, Address address, String contact, String category, IntroContent content, Member user, FileEntity file) {
        this.restaurantNm = restaurantNm;
        this.address = address;
        this.contact = contact;
        this.category = category;
        this.content = content;
        this.user = user;
        this.file = file;
    }

    public void setRestaurant(String restaurantNm, String zipcode, String streetNm, String detailAddress, 
            String contact, String category, String simpleContents, String detailContents, Member user) {

        this.restaurantNm = restaurantNm;
        this.contact = contact;
        this.category = category;
        this.user = user;

        Address address = Address.createAddress(zipcode, streetNm, detailAddress);
        this.address = address;

        IntroContent content = new IntroContent(simpleContents, detailContents);
        this.content = content;
    }
}
