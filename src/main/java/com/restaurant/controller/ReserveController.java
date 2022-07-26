package com.restaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReserveController {
    
    @GetMapping("/reserveForm")
    public String reserveForm(Model model) {

        model.addAttribute("contents", "user/reserve/reserveForm");
        return "common/subLayout";
    }
}
