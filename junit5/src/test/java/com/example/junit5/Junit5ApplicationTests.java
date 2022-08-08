package com.example.junit5;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class Junit5ApplicationTests {

    @Test
    public void 테스트1() {
        String str = "가위";
        assertThat(str).as("기대값 {바위}와 현재값 {%s}가 일치하지 않습니다.", str)
                .isEqualTo("바위");
    }

    /**
     * 예외 발생!이라는 예외를 던지고, hasMessageContaining에서 해당 예외 메시지와 일치한지 검증한다.
     */
    @Test
    public void testException_01() {
        assertThatThrownBy(() -> {
            throw new Exception("예외 발생!");
        })
                .isInstanceOf(Exception.class)
                .hasMessageContaining("예외 발생!");
    }

    /**
     * 대표적인 4가지 예외에 포함 되었다면 아래와 같이 작성할 수 있다.
     */
    @Test
    public void testException_02() {
        assertThatIOException().isThrownBy(() -> {
                    throw new IOException("예외 발생!");
                })
                .withMessage("%s!", "예외 발생!")
                .withMessageContaining("예외 발생!")
                .withNoCause();
    }

    /**
     * 대표적인 4가지 예외에 포함되지 않았다면 아래와 같이 작성하면 된다.
     */
    @Test
    public void testException_03() {
        assertThatExceptionOfType(IOException.class)
                .isThrownBy(() -> {
                    throw new IOException("예외 발생!");
                })
                .withMessage("%s", "예외 발생!")
                .withMessageContaining("예외 발생!")
                .withNoCause();
    }

    /**
     * BDD(행동 주도 개발) 방식으로 작성하면 아래와 같다.
     */
    @Test
    public void testException_04() {
        //given

        //when
        Throwable thrown = catchThrowable(() -> {
            throw new Exception("예외 던지기!");
        });

        //then
        assertThat(thrown)
                .isInstanceOf(Exception.class)
                .hasMessageContaining("예외 던지기!");
    }
}
