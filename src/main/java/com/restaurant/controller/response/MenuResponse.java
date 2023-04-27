package com.restaurant.controller.response;

import com.restaurant.entity.Menu;
import com.restaurant.entity.MenuImage;
import com.restaurant.entity.common.Content;
import com.restaurant.entity.type.MenuType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MenuResponse {

    private Long id;
    private String menuName;
    private Integer price;
    private MenuType menuType;
    private Content content;
    private List<MenuImage> menuFiles = new ArrayList<>();

    public MenuResponse(Menu menu) {
        this.id = menu.getId();
        this.menuName = menu.getMenuName();
        this.price = menu.getPrice();
        this.menuType = menu.getMenuType();
        this.content = menu.getContent();
        this.menuFiles = menu.getMenuImages();
    }
}
