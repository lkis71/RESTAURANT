package com.restaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {
    
    @GetMapping("/login")
    public String login(Model model) {

        model.addAttribute("contents", "login/login");
        return "common/subLayout";
    }
}
