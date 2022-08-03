package com.restaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.restaurant.controller.request.RestaurantRequest;
import com.restaurant.entity.FileEntity;
import com.restaurant.entity.Restaurant;
import com.restaurant.entity.User;
import com.restaurant.service.FileService;
import com.restaurant.service.RestaurantService;
import com.restaurant.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class RestaurantController {

    private final UserService userService;
    private final RestaurantService restaurantService;
    private final FileService fileService;

    @GetMapping("/restaurants")
    public String restaurantList(Model model) {

        model.addAttribute("contents", "restaurant/restaurantList");
        return "common/subLayout";
    }
    
    @GetMapping("/restaurants/{id}/new")
    public String restaurantForm(Model model, @PathVariable("id") Long userId) {
        
        model.addAttribute("userId", userId);
        model.addAttribute("restaurant", new Restaurant());
        model.addAttribute("contents", "restaurant/instRestaurantForm");
        return "common/subLayout";
    }

    @PostMapping("/restaurants/{id}/new")
    @ResponseBody
    public String restaurantForm(Model model, @PathVariable("id") Long userId, RestaurantRequest restReq, @RequestParam("file") MultipartFile file) {

        User user = userService.getUserById(userId);

        Restaurant restaurant = Restaurant.createRestaurant(restReq.getRestaurantNm(), restReq.getZipcode(), restReq.getStreetNm(), 
                restReq.getDetailAddress(), restReq.getContact(), restReq.getCategory(), restReq.getSimpleContents(), restReq.getDetailContents(), user);

        restaurantService.insertRestaurant(restaurant);

        FileEntity fileInfo = FileService.uploadFile(file, "r");
        FileEntity fileEntity = FileEntity.createFile(fileInfo, restaurant, null);

        fileService.insertFile(fileEntity);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("result", "Y");
        
        return new Gson().toJson(jsonObject);
    }

    @GetMapping("/restaurants/{id}/update")
    public String updateRestaurantForm(Model model, @PathVariable("id") Long restId) {

        Restaurant restaurant = restaurantService.getRestaurantById(restId);

        model.addAttribute("restaurant", restaurant);
        model.addAttribute("contents", "restaurant/instRestaurantForm");
        return "common/subLayout";
    }

    @PostMapping("/restaurants/{id}/update")
    @ResponseBody
    public String updateRestaurant(Model model, @PathVariable("id") Long restId, RestaurantRequest restReq, @RequestParam("file") MultipartFile file) {

        restaurantService.updateRestaurant(restId, restReq, file);

        return new Gson().toJson("");
    }
}
