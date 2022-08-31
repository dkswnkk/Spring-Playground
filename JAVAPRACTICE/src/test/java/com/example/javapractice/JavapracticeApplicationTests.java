package com.example.javapractice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JavapracticeApplicationTests {

    @FunctionalInterface
    interface MyFunction {
        public abstract int max(int a, int b);
    }

    @FunctionalInterface
    interface MyFunction2 {
        void run();
    }

    @Test
    void 람다식_작성하기() {
        //given
        MyFunction myFunction = (int a, int b) -> Math.max(a, b);   // 람다식을 이용하여 구현

        //when
        int result = myFunction.max(2, 3);

        //then
        assertEquals(3, result);
    }

    @Test
    void 람다식_구현하기() {
        //given
        MyFunction2 myFunction2 = () -> System.out.println("run");
        //when
        myFunction2.run();
        //then
    }


    @Test
    @DisplayName("isEmpty")
    public void isEmptyTest() {
        assertTrue(StringUtils.isEmpty(null));
        assertTrue(StringUtils.isEmpty(""));
        assertFalse(StringUtils.isEmpty(" ")); // 공백은 False
    }

    @Test
    @DisplayName("hasLength")
    public void hasLengthTest() {
        assertTrue(StringUtils.hasLength("길이를 가지고 있음"));
        assertTrue(StringUtils.hasLength(" ")); // 공백은 True
        assertFalse(StringUtils.hasLength(""));
        assertFalse(StringUtils.hasLength(null));
    }

    @Test
    @DisplayName("hasText")
    public void hasTestTest() {
        assertTrue(StringUtils.hasText("문자열"));
        assertFalse(StringUtils.hasText(""));
        assertFalse(StringUtils.hasText(" "));  // 공백은 False
        assertFalse(StringUtils.hasText(null));
    }

    @Test
    @DisplayName("containsWhitespace")
    public void containsWhitespaceTest() {
        /*
            hasLength()를 먼저 호출 후, True일시 공백을 체크합니다.
         */
        assertTrue(StringUtils.containsWhitespace("공백은 포함하고 있습니다."));
        assertFalse(StringUtils.containsWhitespace("공백은포함하지않고있습니다."));
        assertFalse(StringUtils.containsWhitespace(""));
        assertFalse(StringUtils.containsWhitespace(null));
    }

    @Test
    @DisplayName("trimLeadingWhitespace")
    public void trimLeadingWhitespaceTest() {
        assertThat("문자열 앞의 공백을 제거했습니다.").isEqualTo(StringUtils.trimLeadingWhitespace("   문자열 앞의 공백을 제거했습니다."));
    }

    @Test
    @DisplayName("trimTrailingWhitespace")
    public void trimTrailingWhitespaceTest() {
        assertThat("문자열 뒤의 공백을 제거했습니다.").isEqualTo(StringUtils.trimTrailingWhitespace("문자열 뒤의 공백을 제거했습니다.   "));
    }

    @Test
    @DisplayName("trimWhitespace")
    public void trimWhitespaceTest() {
        assertThat("문자열 앞뒤로 공백을 제거했습니다.").isEqualTo(StringUtils.trimWhitespace("   문자열 앞뒤로 공백을 제거했습니다.  "));
    }

    @Test
    @DisplayName("trimAllWhitespace")
    public void trimAllWhitespace() {
        assertThat("문자열의모든공백을제거했습니다.").isEqualTo(StringUtils.trimAllWhitespace("   문자열의 모든 공백을 제거했습니다."));
    }

    @Test
    @DisplayName("trimLeadingCharacter")
    public void trimLeadingCharacterTest() {
        assertThat("별표제거").isEqualTo(StringUtils.trimLeadingCharacter("*별표제거", '*'));
    }

    @Test
    @DisplayName("trimTrailingCharacter")
    public void trimTrailingCharacterTest() {
        assertThat("별표제거").isEqualTo(StringUtils.trimTrailingCharacter("별표제거*", '*'));
    }

    @Test
    @DisplayName("countOccurrencesOf")
    public void countOccurrencesOfTest() {
        assertThat(StringUtils.countOccurrencesOf("apple", "p")).isEqualTo(2);
        assertThat(StringUtils.countOccurrencesOf("apple", "c")).isEqualTo(0);
    }

    @Test
    @DisplayName("startWithIgnoreCase")
    public void startWithIgnoreCaseTest() {
        assertTrue(StringUtils.startsWithIgnoreCase("010-1234-5678", "010"));
        assertFalse(StringUtils.startsWithIgnoreCase("011-1234-5678", "010"));
    }

    @Test
    @DisplayName("endsWithIgnoreCase")
    public void endsWithIgnoreCaseTest() {
        assertTrue(StringUtils.endsWithIgnoreCase("010-1234-5678", "5678"));
        assertFalse(StringUtils.endsWithIgnoreCase("010-1234-5678", "1234"));
    }

}
