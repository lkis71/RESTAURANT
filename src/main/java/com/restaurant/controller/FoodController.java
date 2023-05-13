package com.restaurant.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.restaurant.controller.dto.FoodDto;
import com.restaurant.controller.dto.MyFoodDto;
import com.restaurant.controller.response.FoodResponse;
import com.restaurant.entity.Food;
import com.restaurant.entity.type.FoodType;
import com.restaurant.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;
    
    @GetMapping("/restaurant/{id}/foods")
    public String foodList(Model model,
                           @PathVariable("id") Long id,
                           @RequestParam(value = "cursor", defaultValue = "0") Long cursor,
                           @RequestParam(value = "limit", defaultValue = "8") int limit) {

        List<FoodResponse> foods = foodService.findByPaging(id, cursor, limit);

        model.addAttribute("foods", foods);
        model.addAttribute("contents", "member/myFood");
        return "common/subLayout";
    }

    @GetMapping("/foods/info")
    public String foodInfo(Model model) {

        model.addAttribute("contents", "restaurant/food/foodInfo");
        return "common/subLayout";
    }

    @GetMapping("/restaurant/{id}/food/new")
    public String insertFoodPage(Model model, @PathVariable("id") Long restaurantId) {

        model.addAttribute("foodTypes", FoodType.values());
        model.addAttribute("food", new Food());
        model.addAttribute("restaurantId", restaurantId);
        model.addAttribute("contents", "restaurant/food/instFoodForm");
        return "common/subLayout";
    }

    @PostMapping("/restaurant/{id}/food/new")
    @ResponseBody
    public String insertFood(FoodDto foodDto) {

        foodService.save(foodDto);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("result", "Y");

        return new Gson().toJson(jsonObject);
    }

    @GetMapping("/food/{id}/update")
    public String updateFoodPage(Model model, @PathVariable("id") Long foodId) {

        Food food = foodService.findById(foodId);

        model.addAttribute("foodTypes", FoodType.values());
        model.addAttribute("food", food);
        model.addAttribute("contents", "restaurant/food/instFoodForm");
        return "common/subLayout";
    }

    @PostMapping("/food/{id}/update")
    public String updateFood(FoodDto foodDto) {

        foodService.update(foodDto);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("result", "Y");

        return new Gson().toJson(jsonObject);
    }
}
