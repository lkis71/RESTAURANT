package com.restaurant.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.restaurant.controller.request.LoginRequest;
import com.restaurant.entity.User;
import com.restaurant.service.LoginService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    
    @GetMapping("/login")
    public String loginFrom(Model model) {

        model.addAttribute("contents", "login/login");
        return "common/subLayout";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response, LoginRequest loginRequest, Model model) throws IOException {

        User user = loginService.login(request, loginRequest.getHmpgId());

        if(user != null) {
            return "redirect:/restaurants";
        }else {
            return "redirect:/users/join";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();

        return "redirect:/restaurants";
    }
}
