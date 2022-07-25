package com.restaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.restaurant.controller.dto.UserDto;
import com.restaurant.controller.request.UserRequest;
import com.restaurant.entity.User;
import com.restaurant.entity.common.Address;
import com.restaurant.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    
    @GetMapping("/users/new")
    public String userForm(Model model) {

        model.addAttribute("user", new UserDto());
        model.addAttribute("contents", "user/insertUserForm");

        return "common/layout";
    }

    @PostMapping("/users/new")
    public void joinUser(UserRequest userReq) {

        Address address = new Address(userReq.getZipcode(), userReq.getStreetNm(), userReq.getDetailAddress());

        User user = User.createUser(userReq.getHmpgId(), userReq.getPassword(), userReq.getUserNm(), 
            userReq.getPhoneNum(), address, userReq.getRegistNum(), userReq.getUserType());

        userService.join(user);
    }
}
