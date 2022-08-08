package com.example.junit5;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Junit5ApplicationTests {

    @Test
    public void 테스트1() {
        String str = "가위";
        Assertions.assertThat(str).as("값이 일치하지 않습니다. 현재 값: %s", str)
                .isEqualTo("바위");
    }

}
