package com.example.logtracking;

import com.example.logtracking.trace.logtrace.FieldLogTrace;
import com.example.logtracking.trace.logtrace.LogTrace;
import com.example.logtracking.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }
}
