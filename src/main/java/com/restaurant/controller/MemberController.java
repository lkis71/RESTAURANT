package com.restaurant.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.restaurant.controller.dto.MyMenuDto;
import com.restaurant.controller.dto.MyRestaurantDto;
import com.restaurant.controller.dto.MemberDto;
import com.restaurant.controller.dto.MemberUpdateDto;
import com.restaurant.controller.request.MenuRequest;
import com.restaurant.entity.Member;
import com.restaurant.entity.Menu;
import com.restaurant.entity.Restaurant;
import com.restaurant.entity.common.Address;
import com.restaurant.exception.AlreadyExistMemberIdException;
import com.restaurant.service.MemberService;
import com.restaurant.service.MenuService;
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

    private final MemberService userService;
    private final MenuService menuService;
    
    @GetMapping("/users/register")
    public String joinUserForm(Model model) {

        model.addAttribute("user", new Object());
        model.addAttribute("contents", "user/registUserForm");

        return "common/subLayout";
    }

    @PostMapping("/users/register")
    public String joinUser(@RequestBody MemberDto userDto) throws AlreadyExistMemberIdException {

        Address address = Address.createAddress(userDto.getZipcode(), userDto.getStreetName(), userDto.getDetailAddress());

        Member member = Member.builder()
                .memberName(userDto.getMemberName())
                .memberId(userDto.getMemberId())
                .password(userDto.getPassword())
                .phoneNum(userDto.getPhoneNum())
                .memberType(userDto.getMemberType())
                .address(address)
                .build();

        userService.join(member);

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
    public String updateUserInfo(HttpServletRequest request, @PathVariable("id") Long userSeq, @RequestBody MemberUpdateDto userUpdateDto) {

        Member updateMember = userService.update(userUpdateDto);
        CommonSession.setSessionUserInfo(request, updateMember);

        JsonObject obj = new JsonObject(); 
        obj.addProperty("result", "Y");

        return new Gson().toJson(obj);
    }

    @GetMapping("/users/{id}/restaurant")
    public String myRestaurants(Model model, @PathVariable("id") Long userSeq) {

        List<Restaurant> restaurants = userService.getMyRestaurantById(userSeq);
        List<MyRestaurantDto> restaurantDtos = restaurants.stream()
            .map(o -> new MyRestaurantDto(o))
            .collect(Collectors.toList());

        model.addAttribute("userSeq", userSeq);
        model.addAttribute("restaurants", restaurantDtos);
        model.addAttribute("contents", "user/myRestaurant");
        return "common/subLayout";
    }

    @GetMapping("/users/{userSeq}/restaurants/{restId}/menus")
    public String myRestaurantMenu(Model model, @PathVariable("userSeq") Long userSeq, @PathVariable("restId") Long restId) {

        List<Menu> menus = menuService.getMenusByRestId(restId);
        List<MyMenuDto> menuDtos = menus.stream()
            .map(o -> new MyMenuDto(o))
            .collect(Collectors.toList());

        model.addAttribute("userSeq", userSeq);
        model.addAttribute("menus", menuDtos);
        model.addAttribute("contents", "user/myMenu");
        return "common/subLayout";
    }

    @GetMapping("/users/{userSeq}/restaurants/menus/{menuId}")
    public String updateMenuPage(Model model, @PathVariable("userSeq") Long userSeq, @PathVariable("menuId") Long menuId) {

        Menu menu = menuService.getMenuById(menuId);

        model.addAttribute("userSeq", userSeq);
        model.addAttribute("menu", menu);
        model.addAttribute("contents", "restaurant/menu/instMenuForm");
        return "common/subLayout";
    }

    @PostMapping("/users/{userSeq}/restaurants/menus/{menuId}")
    @ResponseBody
    public String updateMenu(Model model, @PathVariable("menuId") Long menuId, MenuRequest menuReq) {

        menuService.updateMenu(menuId, menuReq);

        return new Gson().toJson("");
    }
    
    @DeleteMapping("/users/{userSeq}/restaurants/menus/{menuId}")
    @ResponseBody
    public String deleteMenu(Model model, @PathVariable("menuId") Long menuId) {
        
        menuService.deleteMenu(menuId);
        
        return new Gson().toJson("");
    }
}
