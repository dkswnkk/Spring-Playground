package com.example.junit5;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.mockito.internal.util.Supplier;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)   // @Test 메서드 이름의 언더바를 공백으로 바꿔준다 ex) Test_1 -> Test 1
class StudyTest {

    /*
        Junit5 부터는 class와 method에 굳이 public을 명시하지 안아도 된다.
     */
    @Test
    void create() {
        Study study = new Study(-10, 25);
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

    @Test
    void displayNameGeneration_test() {
        System.out.println("DisplayNameGeneration Test");
    }

    @Test
    @DisplayName("DisplayNameTest")
    void displayNameTest() {
        System.out.println("DisplayNameTest");
    }

    @Test
    @DisplayName("스터디 만들기")
    void create_new_study() {
        Study study = new Study(-10, 25);
        assertNotNull(study);
        /*
            순서를 아무렇게나 해도 상관없지만, 보통 첫번째에 기대값을 넣고, 두번째에 실제 나오는 값을 넣는다.
         */
        assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태값이 DRAFT여야 한다.");
        assertEquals(StudyStatus.DRAFT, study.getStatus(), () -> "스터디를 처음 만들면 상태값이 DRAFT여야 한다.");
        assertEquals(StudyStatus.DRAFT, study.getStatus(), String.valueOf(new Supplier<String>() {
            @Override
            public String get() {
                return "스터디를 처음 만들면 상태값이 DRAFT여야 한다.";
            }
        }));

        /*
            람다식을 써서 메세지를 구현해야 하는 이유.
            문자열 연산의 실행을 테스트가 실패 했을 때만 한다. 람다식이 아닐 경우에는 항상 연산을 수행한다.
            따라서 람다식을 사용하는 것이 성능 입장에서 조금 유리할 수 있다.
         */
        assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 " + StudyStatus.DRAFT + " 상태다.");
        assertEquals(StudyStatus.DRAFT, study.getStatus(), () -> "스터디를 처음 만들면 " + StudyStatus.DRAFT + " 상태다.");

        /*
            assertAll을 다음과 같이 사용하면 전자의 테스트케이스가 실패하더라도 테스트를 종료하지 않고 후자의 테스트케이스까지 전부 수행한다.
         */
        assertAll(
                () -> assertTrue(study.getLimit() > 0, () -> "스터디 최대 참석 가능 인원은 0보다 커야한다."),
                () -> assertTrue(study.getLimit() > 1, () -> "스터디 최대 참석 가능 인원은 1보다 커야한다.")
        );
    }

    @Test
    @DisplayName("예외 테스트")
    void exception_test() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(10, 18));

        String message = exception.getMessage();
        assertEquals("나이는 19세 이상이어야 합니다.", message);
    }

    @Test
    @DisplayName("특정 시간 안에 실행이 완료되는지 확인")
    void timeout_test() {
        assertTimeout(Duration.ofSeconds(10), () -> new Study(10, 20));

        /*
            시간이 100 millis가 지났더라도 블록안의 모든 구문을 수행하고 종료된다.
         */
        assertTimeout(Duration.ofMillis(100), () -> {
            new Study(10, 20);
            Thread.sleep(300);
            System.out.println("here~");
        });

        /*
            시간이 오버되면 블록안의 구문을 더 이상 수행하지 않고 바로 테스트를 종료시킨다.
            ThreadLocal 을 사용 할 경우 예상치 못한 경우가 발생할 가능성이 있다.
         */
        assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            new Study(10, 20);
            Thread.sleep(300);
            System.out.println("not here~");
        });
    }

    @Test
    @DisplayName("조건에 따라 테스트 실행하기")
    void condition_test() {

        String test_env = System.getenv("TEST_ENV");
        System.out.println(test_env);
        /*
            조건에 만족할 경우에만 다음 테스트로 넘어가라
         */
        assumeFalse("LOCAL".equalsIgnoreCase(test_env));
        System.out.println("here1");

        /*
            조건에 부합한 경우에만 다음의 테스를 수행하고, 아니라면 넘어가라.
         */
        assumingThat("LOCAL".equalsIgnoreCase(test_env), () -> {
            System.out.println("her e2");
        });

        System.out.println("here3");
    }

    @Test
    @DisplayName("운영체제가 mac인 경우")
    @EnabledOnOs(OS.MAC)
    void macOS_test() {
        System.out.println("macOS입니다.");
    }

    @Test
    @DisplayName("운영체제가 window인 경우")
    @EnabledOnOs(OS.WINDOWS)
    void windowOS_test() {
        System.out.println("windowOS입니다.");
    }

    @Test
    @DisplayName("운영체제가 window 혹은 mac인 경우")
    @EnabledOnOs({OS.WINDOWS, OS.MAC})
    void windowOS_macOS_test() {
        System.out.println("windowOS 혹은 macOS입니다.");
    }

    @Test
    @DisplayName("특정 자바 버전만 실행해라")
    @EnabledOnJre(JRE.JAVA_11)
    void Jre_test() {
        System.out.println("JAVA_11입니다.");
    }

    /*
        fast 태그는 로컬에서 수행한다고 가정.
     */
    @Test
    @DisplayName("Tag 테스트")
    @Tag("fast")
    void tag1_test() {
        Study actual = new Study(20, 20);
        assertNotNull(actual);
    }

    /*
        slow 태그는 CI 환경에서 수행 한다고 가정.
     */
    @Test
    @DisplayName("Tag 테스트")
    @Tag("slow")
    void tag2_test() {
        Study actual = new Study(20, 20);
        assertNotNull(actual);
    }


}