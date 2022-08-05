package com.restaurant.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.restaurant.entity.common.IntroContent;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Menu {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menuId")
    private Long id;

    private String menuNm;

    private String price;

    private IntroContent content;

    private String category;

    @JoinColumn(name = "restaurantId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;
}
