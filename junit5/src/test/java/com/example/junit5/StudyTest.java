package com.example.junit5;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class StudyTest {

    /*
        Junit5 부터는 class와 method에 굳이 public을 명시하지 안아도 된다.
     */
    @Test
    void create() {
        Study study = new Study();
        assertNotNull(study);
        System.out.println("create");
    }

    @Test
    void create1() {
        System.out.println("create1");
    }

    /*
        이 테스트는 실행되지 않는다.
     */
    @Test
    @Disabled
    void create2() {
        System.out.println("create2");
    }

    /*
        모든 테스트가 실행하기 전 딱 한번만 호출된다.
        반드시 static method로 선언해야 한다.
        private 불가, default 가능, return type 불가능 오직 void만
     */
    @BeforeAll
    static void beforeAll() {
        System.out.println("before all");
    }

    /*
        모든 테스트가 실행된 후 딱 한번만 호출
        @BeforeAll과 조건은 동일하다.
     */
    @AfterAll
    static void afterAll() {
        System.out.println("after all");
    }

    /*
        각각의 테스트가 실행되기 전 호출된다.
     */
    @BeforeEach
    void beforeEach() {
        System.out.println("Before Each");
    }

    /*
        각각의 테스트가 실행된 후 호출된다.
     */
    @AfterEach
    void afterEach() {
        System.out.println("After Each");
    }


}