package com.restaurant.repository;

import org.springframework.stereotype.Repository;

import com.restaurant.trace.TraceStatus;
import com.restaurant.trace.logtrace.LogTrace;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LogTraceRepository {
    
    private final LogTrace trace;

    public void logTraceTest() {
        TraceStatus status = trace.begin("repository");
        trace.end(status);
    }
}
