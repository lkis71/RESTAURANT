package com.restaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MenuController {
    
    @GetMapping("/menus")
    public String menuList(Model model) {
        
        model.addAttribute("contents", "restaurant/menu/menuList");
        return "common/subLayout";
    }

    @GetMapping("/menus/info")
    public String menuInfo(Model model) {

        model.addAttribute("contents", "restaurant/menu/menuInfo");
        return "common/subLayout";
    }
}
