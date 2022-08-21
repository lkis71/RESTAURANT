package com.restaurant.controller.dto;

import com.restaurant.entity.FileEntity;
import com.restaurant.entity.Menu;
import com.restaurant.entity.common.IntroContent;

import lombok.Data;

@Data
public class MenuDto {
    
    private Long id;
    private String menuNm;
    private Integer price;
    private IntroContent content;
    private FileEntity file;

    public MenuDto(Menu menu) {
        this.id = menu.getId();
        this.menuNm = menu.getMenuNm();
        this.price = menu.getPrice();
        this.content = menu.getContent();
        this.file = menu.getFile();
    }
}
