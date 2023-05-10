package com.restaurant.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.restaurant.controller.dto.*;
import com.restaurant.controller.response.FoodResponse;
import com.restaurant.entity.Member;
import com.restaurant.entity.Food;
import com.restaurant.entity.Restaurant;
import com.restaurant.entity.type.FoodType;
import com.restaurant.entity.type.MemberType;
import com.restaurant.exception.AlreadyExistMemberIdException;
import com.restaurant.service.MemberService;
import com.restaurant.service.FoodService;
import com.restaurant.service.RestaurantService;
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
    private final RestaurantService restaurantService;
    private final FoodService foodService;

    @GetMapping("/members/join")
    public String joinUserForm(Model model) {

        model.addAttribute("memberTypes", MemberType.values());
        model.addAttribute("member", new Object());
        model.addAttribute("contents", "member/joinForm");

        return "common/subLayout";
    }

    @PostMapping("/members/join")
    public String joinUser(MemberDto memberDto) {

        memberService.join(memberDto);

        return "redirect:/restaurants";
    }

    @GetMapping("/mypage")
    public String mypage(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("MEMBER_INFO");

        model.addAttribute("memberTypes", MemberType.values());
        model.addAttribute("member", member);
        model.addAttribute("contents", "member/mypage");

        return "common/subLayout";
    }

    @GetMapping("/members/reserve")
    public String myReserve(Model model) {

        model.addAttribute("contents", "member/reserve/myReserveList");

        return "common/subLayout";
    }

    @PostMapping("/members/{id}/update")
    @ResponseBody
    public String updateUserInfo(HttpServletRequest request, MemberDto memberDto) {

        Member updateMember = memberService.update(memberDto);
        CommonSession.setSessionUserInfo(request, updateMember);

        JsonObject obj = new JsonObject();
        obj.addProperty("result", "Y");

        return new Gson().toJson(obj);
    }

    @GetMapping("/members/{id}/restaurant")
    public String myRestaurants(Model model, @PathVariable("id") String memberId) {

        List<MyRestaurantDto> myRestaurantDtos = restaurantService.findRestaurantByMemberId(memberId);

        model.addAttribute("memberId", memberId);
        model.addAttribute("myRestaurants", myRestaurantDtos);
        model.addAttribute("contents", "member/myRestaurant");
        return "common/subLayout";
    }

    @GetMapping("/members/{memberId}/restaurants/foods/{foodId}")
    public String updatePage(Model model, @PathVariable("memberId") String memberId, @PathVariable("foodId") Long foodId) {

        Food food = foodService.findById(foodId);

        model.addAttribute("memberId", memberId);
        model.addAttribute("food", food);
        model.addAttribute("contents", "restaurant/food/instFoodForm");
        return "common/subLayout";
    }

    @PostMapping("/members/{memberId}/restaurants/foods/{foodId}")
    @ResponseBody
    public String update(Model model, FoodDto foodDto) {

        foodService.update(foodDto);

        return new Gson().toJson("");
    }

    @DeleteMapping("/members/{memberId}/restaurants/foods/{foodId}")
    @ResponseBody
    public String delete(Model model, @PathVariable("foodId") Long foodId) {

        foodService.delete(foodId);

        return new Gson().toJson("");
    }

    @GetMapping("/login")
    public String loginFrom(Model model) {

        model.addAttribute("contents", "login/login");
        return "common/subLayout";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request, MemberDto memberDto) {

        HttpSession login = memberService.login(request, memberDto);

        if (login.getAttribute("MEMBER_INFO") != null) {
            return "redirect:/restaurants";
        }else {
            return "redirect:/members/join";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();

        return "redirect:/restaurants";
    }
}
