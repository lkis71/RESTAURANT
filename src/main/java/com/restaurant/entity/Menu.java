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

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Menu {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menuId")
    private Long id;

    private String menuName;

    private Integer price;

    private IntroContent content;

    private MenuType menuType;

    @JoinColumn(name = "restaurant_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    @JoinColumn(name = "file_id")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private FileEntity file;

    @Builder
    public Menu(Long id, String menuName, Integer price, IntroContent content, MenuType menuType, Restaurant restaurant, FileEntity file) {
        this.id = id;
        this.menuName = menuName;
        this.price = price;
        this.content = content;
        this.menuType = menuType;
        this.restaurant = restaurant;
        this.file = file;
    }

    public void update(String menuName, Integer price, String simpleContext, String detailContent, MenuType menuType) {

    }
}
