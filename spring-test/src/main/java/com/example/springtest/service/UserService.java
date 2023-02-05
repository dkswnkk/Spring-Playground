package com.example.springtest.service;

import com.example.springtest.api.dto.UserRequestDto;
import com.example.springtest.repository.entity.User;
import com.example.springtest.repository.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void signUp(UserRequestDto userRequestDto) {
        User user = new User(userRequestDto.getEmail(), userRequestDto.getPassword());
        userRepository.save(user);
        throw new RuntimeException("Throw Force Exception");
    }

}
