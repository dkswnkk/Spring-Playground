package com.example.junit5;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class Junit5ApplicationTests {

    @Test
    public void 테스트1() {
        String str = "가위";
        assertThat(str).as("기대값 {바위}와 현재값 {%s}가 일치하지 않습니다.", str)
                .isEqualTo("바위");
    }

}
