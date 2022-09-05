package com.restaurant.service;

import org.springframework.stereotype.Service;

import com.restaurant.repository.LogTraceRepository;
import com.restaurant.trace.TraceStatus;
import com.restaurant.trace.logtrace.LogTrace;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogTraceService {
    
    private final LogTraceRepository repository;
    private final LogTrace trace;

    public void logTraceTest() throws Exception {
        TraceStatus status = trace.begin("service");
        Thread.sleep(1000); //동시성 테스트
        repository.logTraceTest();
        trace.end(status);
    }
}
