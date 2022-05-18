package com.example.APIPractice.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserJpaControllerTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void 테스트1() {
        //given

        //when
        List<User> users = userRepository.findAll();

        //then

        assertFalse(users.isEmpty());
        assertEquals(3, users.size());
    }

    @Test
    void 테스트2() {
        //given

        //when
        Optional<User> user = userRepository.findById(1);

        //then
//        assertFalse(user.isEmpty());
        assertFalse(user.get().getPosts().isEmpty());
    }

}