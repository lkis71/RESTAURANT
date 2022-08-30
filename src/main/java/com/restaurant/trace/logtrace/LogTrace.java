package com.restaurant.trace.logtrace;

import com.restaurant.trace.TraceStatus;

public interface LogTrace {
 
    TraceStatus begin(String message);

    void end(TraceStatus status);

    void exception(TraceStatus status, Exception e);
}
