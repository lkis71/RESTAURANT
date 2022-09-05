package com.restaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.restaurant.service.LogTraceService;
import com.restaurant.trace.TraceStatus;
import com.restaurant.trace.logtrace.LogTrace;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LogTraceController {
    
    private final LogTraceService service;
    private final LogTrace trace;

    @ResponseBody
    @GetMapping("/logTrace/test")
    public String logTraceTest() throws Exception {

        TraceStatus status = trace.begin("controller");
        service.logTraceTest();
        trace.end(status);

        return "ok";
    }
}
