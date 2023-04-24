package com.restaurant.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.restaurant.controller.dto.MenuDto;
import com.restaurant.entity.FileEntity;
import com.restaurant.entity.Menu;
import com.restaurant.entity.Restaurant;
import com.restaurant.entity.common.IntroContent;
import com.restaurant.service.FileService;
import com.restaurant.service.MenuService;
import com.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;

@Controller
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;
    
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

    //메뉴 등록페이지
    @GetMapping("/menu/{id}/new")
    public String insertMenuPage(Model model, @PathVariable("id") Long restaurantId) {

        model.addAttribute("restaurantId", restaurantId);
        model.addAttribute("contents", "restaurant/menu/instMenuForm");
        return "common/subLayout";
    }

    @PostMapping("/menu/{id}/new")
    @ResponseBody
    public String insertMenu(Model model, @PathVariable("id") Long restaurantId, MenuDto menuDto) {

        menuService.insertMenu(restaurantId, menuDto);
        
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("result", "Y");
        
        return new Gson().toJson(jsonObject);
    }
}
