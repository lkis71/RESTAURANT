package com.restaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class RestaurantController {
    
    @GetMapping("/restaurants/new")
    public String restaurantForm(Model model) {
        
        model.addAttribute("contents", "restaurant/insertRestaurantForm");
        return "common/layout";
    }
}
