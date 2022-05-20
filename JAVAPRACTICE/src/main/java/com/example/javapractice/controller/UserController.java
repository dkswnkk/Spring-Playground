package com.example.javapractice.controller;

import com.example.javapractice.domain.user.User;
import com.example.javapractice.exception.UserNotFoundException;
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

    @GetMapping("/users/{id}")
    public ResponseEntity<Integer> getUsers(@PathVariable int id) {
        if (id == 1) {
            throw new UserNotFoundException(String.format("아이디 %d을 가진 유저가 존재하지 않습니다.", id));
        }
        return ResponseEntity.ok(id);
    }
}
