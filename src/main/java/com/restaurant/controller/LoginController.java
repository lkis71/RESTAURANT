package com.restaurant.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.restaurant.entity.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.restaurant.controller.request.LoginRequest;
import com.restaurant.service.LoginService;
import com.restaurant.util.CommonSession;

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

        Member user = loginService.login(request, loginRequest);

        if(user != null) {
            CommonSession.setSessionUserInfo(request, user);
            return "redirect:/restaurants";
        }else {
            return "redirect:/users/register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();

        return "redirect:/restaurants";
    }
}
