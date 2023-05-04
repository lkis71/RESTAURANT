package com.restaurant.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.restaurant.controller.dto.RestaurantDto;
import com.restaurant.controller.response.RestaurantResponse;
import com.restaurant.entity.Restaurant;
import com.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    //목록
    @GetMapping("/restaurants")
    public String restaurantList(Model model,
        @RequestParam(value = "cursor", defaultValue = "0") Long cursor,
        @RequestParam(value = "limit", defaultValue = "8") int limit,
        @RequestParam(value = "currPageIdx", defaultValue = "1") int currPageIdx) {

        List<Restaurant> restaurants = restaurantService.findByPaging(cursor, limit);
        List<RestaurantResponse> restaurantResponses = restaurants.stream()
            .map(o -> new RestaurantResponse(o))
            .collect(Collectors.toList());

        Map<String, Object> pagingInfo = new HashMap<>();
        int maxCnt = restaurantService.count();
        pagingInfo.put("maxCnt", maxCnt);
        pagingInfo.put("limit", limit);
        pagingInfo.put("currPageIdx", currPageIdx);
        
        model.addAttribute("pagingInfo", pagingInfo);
        model.addAttribute("restaurants", restaurantResponses);
        model.addAttribute("contents", "restaurant/restaurantList");

        return "common/subLayout";
    }
    
    //등록페이지
    @GetMapping("/restaurants/{id}/new")
    public String restaurantPage(Model model, @PathVariable("id") Long userSeq) {
        
        model.addAttribute("userSeq", userSeq);
        model.addAttribute("restaurant", new Restaurant());
        model.addAttribute("contents", "restaurant/instRestaurantForm");
        return "common/subLayout";
    }

    //등록
    @PostMapping("/restaurants/{id}/new")
    public String restaurantForm(Model model, @RequestBody RestaurantDto restaurantDto) {

        Long restaurantId = restaurantService.save(restaurantDto);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("result", "Y");
        
        return new Gson().toJson(jsonObject);
    }

    //수정페이지
    @GetMapping("/restaurants/{id}/update")
    public String updatePage(Model model, @PathVariable("id") Long restaurantId) {

        Restaurant restaurant = restaurantService.findOne(restaurantId);

        model.addAttribute("restaurant", restaurant);
        model.addAttribute("contents", "restaurant/instRestaurantForm");
        return "common/subLayout";
    }

    //수정
    @PostMapping("/restaurants/{id}/update")
    public String update(Model model, @PathVariable("id") Long restaurantId, @RequestBody RestaurantDto restaurantDto) {

        restaurantService.update(restaurantId, restaurantDto);

        return new Gson().toJson("");
    }

    //삭제
    @PostMapping("/restaurants/{id}/delete")
    public String delete(Model model, @PathVariable("id") Long restaurantId) {

        restaurantService.delete(restaurantId);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("result", "Y");
        return new Gson().toJson(jsonObject);
    }
}
