package com.restaurant.controller.response;

import com.restaurant.entity.FileEntity;
import com.restaurant.entity.Menu;
import com.restaurant.entity.MenuType;
import com.restaurant.entity.common.IntroContent;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class MenuResponse {

    private Long id;
    private String menuName;
    private Integer price;
    private MenuType menuType;
    private IntroContent content;
    private FileEntity file;

    public MenuResponse(Menu menu) {
        this.id = menu.getId();
        this.menuName = menu.getMenuName();
        this.price = menu.getPrice();
        this.menuType = menu.getMenuType();
        this.content = menu.getContent();
        this.file = menu.getFile();
    }
}
