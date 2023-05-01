package com.restaurant.controller.response;

import com.restaurant.entity.Food;
import com.restaurant.entity.FoodFile;
import com.restaurant.entity.common.Content;
import com.restaurant.entity.type.FoodType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class FoodResponse {

    private Long id;
    private String foodName;
    private Integer price;
    private FoodType foodType;
    private Content content;
    private List<FoodFile> foodFiles = new ArrayList<>();

    public FoodResponse(Food food) {
        this.id = food.getId();
        this.foodName = food.getFoodName();
        this.price = food.getPrice();
        this.foodType = food.getFoodType();
        this.content = food.getContent();
        this.foodFiles = food.getFoodFiles();
    }
}
