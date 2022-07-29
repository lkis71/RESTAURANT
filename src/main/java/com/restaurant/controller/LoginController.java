package com.restaurant.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.restaurant.controller.request.LoginRequest;
import com.restaurant.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    
    @GetMapping("/login")
    public String loginFrom(Model model) {

        model.addAttribute("contents", "login/login");
        return "common/subLayout";
    }

    @PostMapping("/login")
    public String login(HttpServletResponse response, LoginRequest loginRequest, Model model) throws IOException {

        boolean isJoin = userService.checkJoinUser(loginRequest);

        if(isJoin == true) {
            return "redirect:/restaurants";
        }else {
            return "redirect:/users/join";
        }
    }
}
