package com.restaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.restaurant.controller.request.reserveRequest;
import com.restaurant.service.ReserveService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReserveController {

    private final ReserveService reserveService;
    
    @GetMapping("/reserve")
    public String reserveForm(Model model) {

        return "user/reserve/reserveForm";
    }

    @PostMapping("/reserve")
    @ResponseBody
    public String reserve(Model model, reserveRequest reserveReq) {

        reserveService.reserve(reserveReq);

        return "";
    }
}
