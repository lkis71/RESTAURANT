package com.restaurant.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.restaurant.controller.dto.MyMenuDto;
import com.restaurant.controller.dto.MyRestaurantDto;
import com.restaurant.controller.request.MenuRequest;
import com.restaurant.controller.request.UserRequest;
import com.restaurant.entity.Menu;
import com.restaurant.entity.Restaurant;
import com.restaurant.entity.User;
import com.restaurant.service.MenuService;
import com.restaurant.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {

    private static final String SESSION_INFO = "SESSION_INFO";

    private final UserService userService;
    private final MenuService menuService;
    
    @GetMapping("/users/register")
    public String joinUserForm(Model model) {

        model.addAttribute("user", new User());
        model.addAttribute("contents", "user/registUserForm");

        return "common/subLayout";
    }

    @PostMapping("/users/register")
    public String joinUser(UserRequest userReq) {

        User user = User.createUser(userReq.getHmpgId(), userReq.getPassword(), userReq.getUserNm(), userReq.getPhoneNum(), 
                userReq.getZipcode(), userReq.getStreetNm(), userReq.getDetailAddress(), userReq.getRegistNum(), userReq.getUserType());

        userService.join(user);

        return "redirect:/restaurants";
    }
    
    @GetMapping("/mypage")
    public String mypage(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SESSION_INFO);

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
    public String updateUserInfo(HttpServletRequest request, @PathVariable("id") Long userId, UserRequest userRequest) {

        userService.updateUserInfo(request, userId, userRequest);

        JsonObject obj = new JsonObject(); 
        obj.addProperty("result", "Y");

        return new Gson().toJson(obj);
    }

    @GetMapping("/users/{id}/restaurant")
    public String myRestaurants(Model model, @PathVariable("id") Long userId) {

        List<Restaurant> restaurants = userService.getMyRestaurantById(userId);
        List<MyRestaurantDto> restaurantDtos = restaurants.stream()
            .map(o -> new MyRestaurantDto(o))
            .collect(Collectors.toList());

        model.addAttribute("userId", userId);
        model.addAttribute("restaurants", restaurantDtos);
        model.addAttribute("contents", "user/myRestaurant");
        return "common/subLayout";
    }

    @GetMapping("/users/{userId}/restaurants/{restId}/menus")
    public String myRestaurantMenu(Model model, @PathVariable("userId") Long userId, @PathVariable("restId") Long restId) {

        List<Menu> menus = menuService.getMenusByRestId(restId);
        List<MyMenuDto> menuDtos = menus.stream()
            .map(o -> new MyMenuDto(o))
            .collect(Collectors.toList());

        model.addAttribute("userId", userId);
        model.addAttribute("menus", menuDtos);
        model.addAttribute("contents", "user/myMenu");
        return "common/subLayout";
    }

    @GetMapping("/users/{userId}/restaurants/menus/{menuId}")
    public String updateMenuPage(Model model, @PathVariable("userId") Long userId, @PathVariable("menuId") Long menuId) {

        Menu menu = menuService.getMenuById(menuId);

        model.addAttribute("userId", userId);
        model.addAttribute("menu", menu);
        model.addAttribute("contents", "restaurant/menu/instMenuForm");
        return "common/subLayout";
    }

    @PostMapping("/users/{userId}/restaurants/menus/{menuId}")
    @ResponseBody
    public String updateMenu(Model model, @PathVariable("menuId") Long menuId, MenuRequest menuReq) {

        menuService.updateMenu(menuId, menuReq);

        return new Gson().toJson("");
    }
    
    @DeleteMapping("/users/{userId}/restaurants/menus/{menuId}")
    @ResponseBody
    public String deleteMenu(Model model, @PathVariable("menuId") Long menuId) {
        
        menuService.deleteMenu(menuId);
        
        return new Gson().toJson("");
    }
}
