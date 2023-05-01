package com.restaurant.controller;

import com.restaurant.controller.dto.LoginDto;
import com.restaurant.entity.Member;
import com.restaurant.service.LoginService;
import com.restaurant.util.CommonSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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
    public String login(HttpServletRequest request, @RequestBody LoginDto loginDto, Model model) throws IOException {

        Member user = loginService.login(loginDto);

        if(user != null) {
            CommonSession.setSessionUserInfo(request, user);
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
