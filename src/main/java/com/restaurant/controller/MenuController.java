package com.restaurant.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.restaurant.controller.dto.MenuDto;
import com.restaurant.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String savePage(Model model, @PathVariable("id") Long restaurantId) {

        model.addAttribute("restaurantId", restaurantId);
        model.addAttribute("contents", "restaurant/menu/instMenuForm");
        return "common/subLayout";
    }

    @PostMapping("/menu/{id}/new")
    @ResponseBody
    public String save(Model model, @PathVariable("id") Long restaurantId, MenuDto menuDto) {

        menuService.save(restaurantId, menuDto);
        
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("result", "Y");
        
        return new Gson().toJson(jsonObject);
    }
}
