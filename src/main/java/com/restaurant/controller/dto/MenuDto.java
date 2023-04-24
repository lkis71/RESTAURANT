package com.restaurant.controller.dto;

import com.restaurant.entity.FileEntity;
import com.restaurant.entity.Menu;
import com.restaurant.entity.MenuType;
import com.restaurant.entity.common.IntroContent;

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
}
