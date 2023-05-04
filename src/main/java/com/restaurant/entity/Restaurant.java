package com.restaurant.entity;

import javax.persistence.*;

import com.restaurant.controller.dto.RestaurantDto;
import com.restaurant.entity.common.Address;
import com.restaurant.entity.common.Content;

import com.restaurant.entity.type.RestaurantType;
import com.restaurant.entity.type.UseType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Restaurant {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
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
    private Content content;

    @Setter
    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Setter
    @OneToMany(mappedBy = "restaurant")
    private List<RestaurantFile> restaurantFiles = new ArrayList<>();

    @Setter
    @Enumerated(EnumType.STRING)
    private UseType useType;

    @Builder
    public Restaurant(String restaurantName, Address address, String contact, RestaurantType restaurantType, Content content, Member member) {
        this.restaurantName = restaurantName;
        this.address = address;
        this.contact = contact;
        this.restaurantType = restaurantType;
        this.content = content;
        this.member = member;
        this.useType = UseType.USE;
    }

    public void update(RestaurantDto restaurantDto) {

        this.restaurantName = restaurantDto.getRestaurantName();
        this.contact = restaurantDto.getContact();
        this.restaurantType = restaurantDto.getRestaurantType();

        Address address = new Address(restaurantDto.getZipcode(), restaurantDto.getStreetName(), restaurantDto.getDetailAddress());
        Content content = new Content(restaurantDto.getSimpleContents(), restaurantDto.getDetailContents());

        this.address = address;
        this.content = content;
    }

    public void delete() {
        this.useType = UseType.REMOVE;

        // 첨부파일 삭제
        for (RestaurantFile restaurantFile : restaurantFiles) {
            restaurantFile.delete();
        }
    }
}
