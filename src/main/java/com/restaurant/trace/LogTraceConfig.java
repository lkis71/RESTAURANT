package com.restaurant.trace;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.restaurant.trace.logtrace.FieldLogTrace;
import com.restaurant.trace.logtrace.LogTrace;

@Configuration
public class LogTraceConfig {
    
    @Bean
    public LogTrace logTrace() {
        return new FieldLogTrace();
    }
}
