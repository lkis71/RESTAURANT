package com.restaurant.controller.request;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class MenuRequest {

    private String menuNm;
    private Integer price;
    private String category;
    private String simpleContents;
    private String detailContents;
    private MultipartFile file;
}
