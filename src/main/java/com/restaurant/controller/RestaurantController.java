package com.restaurant.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.restaurant.controller.dto.MenuDto;
import com.restaurant.controller.dto.RestaurantDto;
import com.restaurant.controller.request.RestaurantRequest;
import com.restaurant.entity.FileEntity;
import com.restaurant.entity.Menu;
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

    //목록
    @GetMapping("/restaurants")
    public String restaurantList(Model model,
        @RequestParam(value = "cursor", defaultValue = "0") int cursor,
        @RequestParam(value = "limit", defaultValue = "8") int limit,
        @RequestParam(value = "currPageIdx", defaultValue = "1") int currPageIdx) {

        List<Restaurant> restaurants = restaurantService.getPagingRestaurant(cursor, limit);
        List<RestaurantDto> restaurantDtos = restaurants.stream()
            .map(o -> new RestaurantDto(o))
            .collect(Collectors.toList());

        Map<String, Object> pagingInfo = new HashMap<>();
        int maxCnt = restaurantService.getRestaurants().size();
        pagingInfo.put("maxCnt", maxCnt);
        pagingInfo.put("limit", limit);
        pagingInfo.put("currPageIdx", currPageIdx);
        
        model.addAttribute("pagingInfo", pagingInfo);
        model.addAttribute("restaurants", restaurantDtos);
        model.addAttribute("contents", "restaurant/restaurantList");
        return "common/subLayout";
    }
    
    //등록페이지
    @GetMapping("/restaurants/{id}/new")
    public String restaurantPage(Model model, @PathVariable("id") Long userId) {
        
        model.addAttribute("userId", userId);
        model.addAttribute("restaurant", new Restaurant());
        model.addAttribute("contents", "restaurant/instRestaurantForm");
        return "common/subLayout";
    }

    //등록
    @PostMapping("/restaurants/{id}/new")
    @ResponseBody
    public String restaurantForm(Model model, @PathVariable("id") Long userId, RestaurantRequest restReq) {

        User user = userService.getUserById(userId);

        Restaurant restaurant = Restaurant.createRestaurant(restReq.getRestaurantNm(), restReq.getZipcode(), restReq.getStreetNm(), 
                restReq.getDetailAddress(), restReq.getContact(), restReq.getCategory(), restReq.getSimpleContents(), restReq.getDetailContents(), user);

        restaurantService.insertRestaurant(restaurant);

        FileEntity file = FileService.uploadFile(restReq.getFile(), "r");
        FileEntity fileEntity = FileEntity.createFile(file, restaurant, null);
        fileService.insertFile(fileEntity);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("result", "Y");
        
        return new Gson().toJson(jsonObject);
    }

    //수정페이지
    @GetMapping("/restaurants/{id}/update")
    public String updateRestaurantPage(Model model, @PathVariable("id") Long restId) {

        Restaurant restaurant = restaurantService.getRestaurant(restId);

        model.addAttribute("restaurant", restaurant);
        model.addAttribute("contents", "restaurant/instRestaurantForm");
        return "common/subLayout";
    }

    //수정
    @PostMapping("/restaurants/{id}/update")
    @ResponseBody
    public String updateRestaurant(Model model, @PathVariable("id") Long restId, RestaurantRequest restReq) {

        restaurantService.updateRestaurant(restId, restReq);

        return new Gson().toJson("");
    }

    //삭제
    @PostMapping("/restaurants/{id}/delete")
    @ResponseBody
    public String deleteRestaurant(Model model, @PathVariable("id") Long restId) {

        restaurantService.deleteRestaurant(restId);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("result", "Y");
        return new Gson().toJson(jsonObject);
    }

    @GetMapping("/restaurants/{id}/menus")
    public String menuList(Model model, @PathVariable("id") Long restId) {
        
        List<Menu> menus = restaurantService.getMenus(restId);
        List<MenuDto> menuDtos = menus.stream()
            .map(o -> new MenuDto(o))
            .collect(Collectors.toList());

        model.addAttribute("menus", menuDtos);
        model.addAttribute("contents", "restaurant/menu/menuList");
        return "common/subLayout";
    }
}
