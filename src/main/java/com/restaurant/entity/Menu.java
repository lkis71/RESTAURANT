package com.restaurant.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

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

    private Integer price;

    private IntroContent content;

    private String category;

    @JoinColumn(name = "restaurantId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    @OneToOne(mappedBy = "menu", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private FileEntity file;

    public static Menu createMenu(String menuNm, Integer price, String simpleContents, String detailContents, String category, Restaurant restaurant) {

        Menu menu = new Menu();
        menu.setMenu(menuNm, price, simpleContents, detailContents, category, restaurant);

        return menu;
    }

    public void setMenu(String menuNm, Integer price, String simpleContents, String detailContents, String category, Restaurant restaurant) {
        this.menuNm = menuNm;
        this.price = price;
        this.category = category;
        this.restaurant = restaurant;

        IntroContent content = new IntroContent(simpleContents, detailContents);
        this.content = content;
    }
}
