package com.example.springtest.api.controller;

import com.example.springtest.api.dto.UserRequestDto;
import com.example.springtest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public void signUp(@RequestBody UserRequestDto userRequestDto) {
        userService.signUp(userRequestDto);
    }
}
