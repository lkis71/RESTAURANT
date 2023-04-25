package com.restaurant.entity;

import javax.persistence.*;

import com.restaurant.entity.common.Address;
import com.restaurant.entity.common.IntroContent;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Restaurant {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurantId")
    private Long id;

    @Setter
    private String restaurantName;

    @Setter
    @Embedded
    private Address address;

    @Setter
    private String contact;

    @Setter
    @Enumerated(EnumType.STRING)
    private RestaurantType restaurantType;

    @Setter
    private IntroContent content;

    @Setter
    @JoinColumn(name = "member_seq")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Setter
    @JoinColumn(name = "file_id")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private FileEntity file;

    @Setter
    @Enumerated(EnumType.STRING)
    private UseType useType;

    @Builder
    public Restaurant(String restaurantName, Address address, String contact, RestaurantType restaurantType, IntroContent content, Member member, FileEntity file) {
        this.restaurantName = restaurantName;
        this.address = address;
        this.contact = contact;
        this.restaurantType = restaurantType;
        this.content = content;
        this.member = member;
        this.file = file;
        this.useType = UseType.USE;
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

    public void delete() {
        this.useType = UseType.REMOVE;
        this.file.setUseType(UseType.REMOVE);
    }
}
