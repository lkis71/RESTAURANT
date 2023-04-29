package com.restaurant.controller.dto;

import com.restaurant.entity.Food;
import com.restaurant.entity.common.Content;
import com.restaurant.entity.type.FoodType;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class FoodDto {
    
    private Long id;
    private String foodName;
    private int price;
    private FoodType foodType;
    private String simpleContents;
    private String detailContents;
    private List<MultipartFile> files = new ArrayList<>();

    @Builder
    public FoodDto(String foodName, Integer price, FoodType foodType, String simpleContents, String detailContents, List<MultipartFile> files) {
        this.foodName = foodName;
        this.price = price;
        this.foodType = foodType;
        this.simpleContents = simpleContents;
        this.detailContents = detailContents;
        this.files = files;
    }

    public Food toEntity() {

        Content content = new Content(simpleContents, detailContents);

        Food food = Food.builder()
                .foodName(foodName)
                .price(price)
                .content(content)
                .foodType(foodType)
                .build();

        return food;
    }
}
