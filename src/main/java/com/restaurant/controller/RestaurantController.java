package com.restaurant.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.restaurant.controller.dto.FoodDto;
import com.restaurant.controller.dto.RestaurantDto;
import com.restaurant.controller.response.RestaurantResponse;
import com.restaurant.entity.Restaurant;
import com.restaurant.entity.type.FoodType;
import com.restaurant.entity.type.RestaurantType;
import com.restaurant.service.FoodService;
import com.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final FoodService foodService;

    //목록
    @GetMapping("/restaurants")
    public String restaurantList(Model model,
                                 @RequestParam(value = "cursor", defaultValue = "0") Long cursor,
                                 @RequestParam(value = "limit", defaultValue = "8") int limit,
                                 @RequestParam(value = "currPageIdx", defaultValue = "1") int currPageIdx) {

        List<RestaurantResponse> restaurantResponses = restaurantService.findByPaging(cursor, limit);

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
    @GetMapping("/restaurants/{memberId}/new")
    public String restaurantPage(Model model, @PathVariable("memberId") String memberId) {

        model.addAttribute("restaurantTypes", RestaurantType.values());
        model.addAttribute("memberId", memberId);
        model.addAttribute("restaurant", new Restaurant());
        model.addAttribute("contents", "restaurant/instRestaurantForm");
        return "common/subLayout";
    }

    //등록
    @PostMapping("/restaurants/{memberId}/new")
    @ResponseBody
    public String restaurantForm(RestaurantDto restaurantDto) {

        restaurantService.save(restaurantDto);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("result", "Y");

        return new Gson().toJson(jsonObject);
    }

    //수정페이지
    @GetMapping("/restaurants/{id}/update")
    public String updatePage(Model model, @PathVariable("id") Long restaurantId) {

        Restaurant restaurant = restaurantService.findOne(restaurantId);

        model.addAttribute("restaurantTypes", RestaurantType.values());
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("contents", "restaurant/instRestaurantForm");
        return "common/subLayout";
    }

    //수정
    @PostMapping("/restaurants/{id}/update")
    @ResponseBody
    public String update(@PathVariable("id") Long restaurantId, RestaurantDto restaurantDto) {

        restaurantService.update(restaurantId, restaurantDto);

        return new Gson().toJson("");
    }

    //삭제
    @PostMapping("/restaurants/{id}/delete")
    @ResponseBody
    public String delete(@PathVariable("id") Long restaurantId) {

        restaurantService.delete(restaurantId);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("result", "Y");
        return new Gson().toJson(jsonObject);
    }

    @GetMapping("/restaurant/{id}/food/new")
    public String savePage(Model model, @PathVariable("id") Long restaurantId) {

        model.addAttribute("foodTypes", FoodType.values());
        model.addAttribute("restaurantId", restaurantId);
        model.addAttribute("contents", "restaurant/food/instFoodForm");
        return "common/subLayout";
    }

    @PostMapping("/restaurant/{id}/food/new")
    @ResponseBody
    public String save(FoodDto foodDto) {

        foodService.save(foodDto);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("result", "Y");

        return new Gson().toJson(jsonObject);
    }
}
