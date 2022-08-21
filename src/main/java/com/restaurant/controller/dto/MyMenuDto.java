package com.restaurant.controller.dto;

import com.restaurant.entity.Menu;
import com.restaurant.entity.common.IntroContent;

import lombok.Getter;

@Getter
public class MyMenuDto {
    
    private Long id;
    private String menuNm;
    private Integer price;
    private IntroContent content;
    private String category;

    public MyMenuDto(Menu menu) {
        this.id = menu.getId();
        this.menuNm = menu.getMenuNm();
        this.price = menu.getPrice();
        this.content = menu.getContent();
        this.category = menu.getCategory();
    }
}
