package com.restaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.restaurant.controller.request.RestaurantRequest;
import com.restaurant.entity.Restaurant;
import com.restaurant.service.FileService;
import com.restaurant.service.RestaurantService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/restaurants")
    public String restaurantList(Model model) {

        model.addAttribute("contents", "restaurant/restaurantList");
        return "common/subLayout";
    }
    
    @GetMapping("/restaurants/{id}/new")
    public String restaurantForm(Model model, @PathVariable("id") Long userSeq) {
        
        model.addAttribute("userSeq", userSeq);
        model.addAttribute("contents", "restaurant/instRestaurantForm");
        return "common/subLayout";
    }

    @PostMapping("/restaurants/{id}/new")
    public String restaurantForm(Model model, RestaurantRequest restReq, @RequestParam("file") MultipartFile file) {

        Restaurant restaurant = Restaurant.createRestaurant(restReq.getRestaurantNm(), restReq.getZipcode(), restReq.getStreetNm(), 
                restReq.getDetailAddress(), restReq.getContact(), restReq.getCategory(), restReq.getSimpleContents(), restReq.getDetailContents());

        restaurantService.insertRestaurant(restaurant);

        FileService.uploadFile(file, "r");


        return "redirect:/mypage";
    }
}
