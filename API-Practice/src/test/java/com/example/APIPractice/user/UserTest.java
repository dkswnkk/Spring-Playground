package com.example.APIPractice.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.DateUtil.now;

@SpringBootTest
class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void BaseEntity_테스트(){
        // given

        LocalDateTime nowTime = LocalDateTime.now();

        User user = User.builder()
                .joinDate(now())
                .name("안주형")
                .password("password1")
                .ssn("981016-3333333")
                .build();

        //when
        userRepository.save(user);

        //then
        assertThat(user.getCreatedTime()).isAfter(nowTime);

    }


}