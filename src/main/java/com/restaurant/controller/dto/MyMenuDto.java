package com.restaurant.controller.dto;

import com.restaurant.entity.Menu;
import com.restaurant.entity.type.MenuType;
import com.restaurant.entity.common.Content;

import lombok.Getter;

@Getter
public class MyMenuDto {
    
    private Long id;
    private String menuName;
    private Integer price;
    private Content content;
    private MenuType menuType;

    public MyMenuDto(Menu menu) {
        this.id = menu.getId();
        this.menuName = menu.getMenuName();
        this.price = menu.getPrice();
        this.content = menu.getContent();
        this.menuType = menu.getMenuType();
    }
}
