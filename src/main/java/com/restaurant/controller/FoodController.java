package com.restaurant.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.restaurant.controller.dto.FoodDto;
import com.restaurant.entity.type.FoodType;
import com.restaurant.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;
    
    @GetMapping("/foods")
    public String foodList(Model model) {
        
        model.addAttribute("contents", "restaurant/food/foodList");
        return "common/subLayout";
    }

    @GetMapping("/foods/info")
    public String foodInfo(Model model) {

        model.addAttribute("contents", "restaurant/food/foodInfo");
        return "common/subLayout";
    }
}
