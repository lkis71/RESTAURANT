package com.restaurant.controller.response;

import com.restaurant.entity.Food;
import com.restaurant.entity.FoodFile;import com.restaurant.entity.FoodFile;
import com.restaurant.entity.common.Content;
import com.restaurant.entity.type.FoodType;
import com.restaurant.entity.type.UseType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class FoodResponse {

    private Long id;
    private String foodName;
    private Integer price;
    private FoodType foodType;
    private Content content;
    private UseType useType;
    private List<FoodFile> foodFiles = new ArrayList<>();
    private Long restaurantId;

    public FoodResponse(Food food) {
        this.id = food.getId();
        this.foodName = food.getFoodName();
        this.price = food.getPrice();
        this.foodType = food.getFoodType();
        this.content = food.getContent();
        this.useType = food.getUseType();
        this.foodFiles = food.getFoodFiles();
        this.restaurantId = food.getRestaurant().getId();
    }
}
