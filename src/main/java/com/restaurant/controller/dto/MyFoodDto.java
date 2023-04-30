package com.restaurant.controller.dto;

import com.restaurant.entity.Food;
import com.restaurant.entity.type.FoodType;
import com.restaurant.entity.common.Content;

import lombok.Getter;

@Getter
public class MyFoodDto {
    
    private Long id;
    private String foodName;
    private Integer price;
    private Content content;
    private FoodType foodType;

    public MyFoodDto(Food food) {
        this.id = food.getId();
        this.foodName = food.getFoodName();
        this.price = food.getPrice();
        this.content = food.getContent();
        this.foodType = food.getFoodType();
    }
}
