package com.restaurant.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.restaurant.controller.dto.*;
import com.restaurant.entity.Member;
import com.restaurant.entity.Food;
import com.restaurant.exception.AlreadyExistMemberIdException;
import com.restaurant.service.MemberService;
import com.restaurant.service.FoodService;
import com.restaurant.util.CommonSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final FoodService foodService;
    
    @GetMapping("/users/register")
    public String joinUserForm(Model model) {

        model.addAttribute("user", new Object());
        model.addAttribute("contents", "user/registUserForm");

        return "common/subLayout";
    }

    @PostMapping("/users/register")
    public String joinUser(@RequestBody MemberDto memberDto) throws AlreadyExistMemberIdException {

        memberService.join(memberDto);

        return "redirect:/restaurants";
    }
    
    @GetMapping("/mypage")
    public String mypage(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        Member user = (Member) session.getAttribute("SESSION_INFO");

        model.addAttribute("user", user);
        model.addAttribute("contents", "user/mypage");
        return "common/subLayout";
    }

    @GetMapping("/users/reserve")
    public String myReserve(Model model) {

        model.addAttribute("contents", "user/reserve/myReserveList");
        return "common/subLayout";
    }

    @PostMapping("/users/{id}/update")
    @ResponseBody
    public String updateUserInfo(HttpServletRequest request, @RequestBody MemberDto memberDto) {

        Member updateMember = memberService.update(memberDto);
        CommonSession.setSessionUserInfo(request, updateMember);

        JsonObject obj = new JsonObject(); 
        obj.addProperty("result", "Y");

        return new Gson().toJson(obj);
    }

    @GetMapping("/users/{id}/restaurant")
    public String myRestaurants(Model model, @PathVariable("id") Long userSeq) {

//        List<Restaurant> restaurants = memberService.getMyRestaurantById(userSeq);
//        List<MyRestaurantDto> restaurantDtos = restaurants.stream()
//            .map(o -> new MyRestaurantDto(o))
//            .collect(Collectors.toList());
//
//        model.addAttribute("userSeq", userSeq);
//        model.addAttribute("restaurants", restaurantDtos);
//        model.addAttribute("contents", "user/myRestaurant");
        return "common/subLayout";
    }

    @GetMapping("/users/{userSeq}/restaurants/{restaurantId}/foods")
    public String myRestaurantfood(Model model, @PathVariable("userSeq") Long userSeq, @PathVariable("restaurantId") Long restaurantId) {

        List<Food> foods = foodService.findByPaging(restaurantId, 0L, 0);
        List<MyFoodDto> foodDtos = foods.stream()
            .map(o -> new MyFoodDto(o))
            .collect(Collectors.toList());

        model.addAttribute("userSeq", userSeq);
        model.addAttribute("foods", foodDtos);
        model.addAttribute("contents", "user/myfood");
        return "common/subLayout";
    }

    @GetMapping("/users/{userSeq}/restaurants/foods/{foodId}")
    public String updatePage(Model model, @PathVariable("userSeq") Long userSeq, @PathVariable("foodId") Long foodId) {

        Food food = foodService.findById(foodId);

        model.addAttribute("userSeq", userSeq);
        model.addAttribute("food", food);
        model.addAttribute("contents", "restaurant/food/instfoodForm");
        return "common/subLayout";
    }

    @PostMapping("/users/{userSeq}/restaurants/foods/{foodId}")
    @ResponseBody
    public String update(Model model, @RequestBody FoodDto foodDto) {

        foodService.update(foodDto);

        return new Gson().toJson("");
    }
    
    @DeleteMapping("/users/{userSeq}/restaurants/foods/{foodId}")
    @ResponseBody
    public String delete(Model model, @PathVariable("foodId") Long foodId) {
        
        foodService.delete(foodId);
        
        return new Gson().toJson("");
    }
}
