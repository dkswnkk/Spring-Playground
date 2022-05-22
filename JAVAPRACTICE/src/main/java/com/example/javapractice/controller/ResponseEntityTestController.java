package com.example.javapractice.controller;

import com.example.javapractice.domain.ResponseFormat;
import com.example.javapractice.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResponseEntityTestController {

    @Autowired

    @GetMapping("/test/users")
    public ResponseFormat<User> getResponseEntity() {

        User user = User.builder()
                .name("안주형")
                .age(25)
                .build();
        return ResponseFormat.ok(user);
    }
}
