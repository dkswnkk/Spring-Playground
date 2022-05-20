package com.example.javapractice.controller;

import com.example.javapractice.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequestMapping("/")
@RestController
public class UserController {

    @PostMapping("/users")
    public ResponseEntity<User> createUsers(@Valid @RequestBody User user) {
        return ResponseEntity.ok(user);
    }
}
