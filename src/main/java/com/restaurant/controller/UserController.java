package com.restaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.restaurant.controller.dto.UserDto;

@Controller
public class UserController {
    
    @GetMapping("/userForm")
    public String userForm(Model model) {

        model.addAttribute("user", new UserDto());
        return "user/insertUserForm";
    }
}
