package com.example.javapractice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JavapracticeApplicationTests {

    @FunctionalInterface
    interface MyFunction {
        public abstract int max(int a, int b);
    }

    @FunctionalInterface
    interface MyFunction2{
        void run();
    }

    @Test
    void 람다식_작성하기() {
        //given
        MyFunction myFunction = (int a, int b) -> Math.max(a, b);   // 람다식을 이용하여 구현

        //when
        int result = myFunction.max(2,3);

        //then
        assertEquals(3,result);
    }

    @Test
    void 람다식_구현하기(){
        //given
        MyFunction2 myFunction2 = ()-> System.out.println("run");
        //when
        myFunction2.run();
        //then
    }





}
