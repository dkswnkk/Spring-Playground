package com.example.logtracking.app.v5;

import com.example.logtracking.trace.callback.TraceCallback;
import com.example.logtracking.trace.callback.TraceTemplate;
import com.example.logtracking.trace.logtrace.LogTrace;
import com.example.logtracking.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderControllerV5 {

    private final OrderServiceV5 orderServiceV5;
    private final TraceTemplate traceTemplate;

    @Autowired
    public OrderControllerV5(OrderServiceV5 orderServiceV5, LogTrace trace) {
        this.orderServiceV5 = orderServiceV5;
        this.traceTemplate = new TraceTemplate(trace);
    }

    @GetMapping("/v5/request")
    public String request(String itemId) {
        return traceTemplate.execute("OrderController.request()", new TraceCallback<String>() {
            @Override
            public String Call() {
                orderServiceV5.orderItem(itemId);
                return "OK";
            }
        });
    }
}
