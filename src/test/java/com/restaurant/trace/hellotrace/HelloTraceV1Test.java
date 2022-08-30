package com.restaurant.trace.hellotrace;

import org.junit.jupiter.api.Test;

import com.restaurant.trace.TraceStatus;

public class HelloTraceV1Test {
    @Test
    void testBegin() {
        HelloTraceV1 trace = new HelloTraceV1();
        TraceStatus status = trace.begin("Hello");
        trace.end(status);
    }

    @Test
    void testEnd() {

    }

    @Test
    void testException() {
        HelloTraceV1 trace = new HelloTraceV1();
        TraceStatus status = trace.begin("Hello");
        trace.exception(status, new IllegalAccessException());
    }
}
