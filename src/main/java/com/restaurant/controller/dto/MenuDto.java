package com.restaurant.controller.dto;

import com.restaurant.entity.Menu;
import com.restaurant.entity.common.Content;
import com.restaurant.entity.type.MenuType;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MenuDto {
    
    private Long id;
    private String menuName;
    private Integer price;
    private MenuType menuType;
    private String simpleContents;
    private String detailContents;
    private MultipartFile file;

    @Builder
    public MenuDto(String menuName, Integer price, MenuType menuType, String simpleContents, String detailContents, MultipartFile file) {
        this.menuName = menuName;
        this.price = price;
        this.menuType = menuType;
        this.simpleContents = simpleContents;
        this.detailContents = detailContents;
        this.file = file;
    }

    public Menu toEntity() {

        Content content = new Content(simpleContents, detailContents);

        Menu menu = Menu.builder()
                .menuName(menuName)
                .price(price)
                .content(content)
                .menuType(menuType)
                .build();

        return menu;
    }
}
