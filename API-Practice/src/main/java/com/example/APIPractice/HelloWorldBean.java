package com.example.APIPractice;

import lombok.Data;

@Data
public class HelloWorldBean {
    private String message;

    public HelloWorldBean(String message) {
        this.message = message;
    }

}
