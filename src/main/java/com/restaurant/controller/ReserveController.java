package com.restaurant.controller;

import com.restaurant.controller.dto.ReserveDto;
import com.restaurant.service.ReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String reserve(Model model, @RequestBody ReserveDto reserveDto) {

        reserveService.reserve(reserveDto);

        return "";
    }
}
