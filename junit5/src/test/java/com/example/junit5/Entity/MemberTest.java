package com.example.junit5.Entity;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.not;
import static org.assertj.core.api.AssertionsForClassTypes.in;
import static org.assertj.core.api.AssertionsForClassTypes.notIn;


@SpringBootTest
class MemberTest {
    public Member member1, member2, member3;
    public List<Member> members;

    @BeforeEach
    public void createMember() {
        member1 = new Member("Kim", 20, MemberRole.ADMIN);
        member2 = new Member("Ahn", 20, MemberRole.BASIC);
        member3 = new Member("Park", 21, MemberRole.VIP);
        members = Lists.list(member1, member2, member3);
    }


    /**
     * 리스트 중 나이가 20살인 멤버들은 member1과 member2만이 유일하다.
     */
    @Test
    public void test1() {
        assertThat(members)
                .filteredOn("age", 20)
                .containsOnly(member1, member2);
    }

    /**
     * 리스트 중 나이 19,20를 포함하지 않은 멤버는 member3이 유일하다.
     */
    @Test
    public void test2() {
        assertThat(members)
                .filteredOn("age", notIn(19, 20))
                .containsOnly(member3);
    }

    /**
     * 리스트 중 나이가 20인 사람은 member1과 member2가 유일하다.
     */
    @Test
    public void test3() {
        assertThat(members)
                .filteredOn("age", in(20))
                .containsOnly(member1, member2);
    }

    /**
     * 리스트 중 이름이 Lee가 아닌 멤버들은 member1, member2, member3이 유일하다.
     */
    @Test
    public void test4() {
        assertThat(members)
                .filteredOn("name", not("Lee"))
                .containsOnly(member1, member2, member3);
    }

    /**
     * 체이닝으로 필터를 이어갈 수도 있다.
     */
    @Test
    public void test5() {
        assertThat(members)
                .filteredOn("age", 21)
                .filteredOn("name", not("Park"))
                .isEmpty();
    }

    /**
     * 람다식으로 조건도 줄 수 있다.
     */
    @Test
    public void test6() {
        assertThat(members)
                .filteredOn("age", 20)
                .filteredOn(member -> member.age > 20)
                .isEmpty();
    }


    /**
     * members의 name을 테스트할 때 아래와 같은 번거로운 방식으로 테스트 할 수 있다.
     */
    @Test
    public void test7() {
        List<String> names = new ArrayList<>();
        for (Member member : members) {
            names.add(member.getName());
        }
        assertThat(names).containsOnly("Kim", "Ahn", "Park");
    }

    /**
     * 테스트 7번을 개선한 코드
     * extracting을 사용하면 간결하게 테스트할 수 있다.
     */
    @Test
    public void test8() {
        assertThat(members)
                .extracting("name")
                .containsOnly("Kim", "Ahn", "Park");
    }

    /**
     * members의 member 이름들은 Kim과 Ahn을 포함하고 Lee는 포함하지 않는다.
     */
    @Test
    public void test8_1() {
        assertThat(members)
                .extracting("name")
                .contains("Kim", "Ahn")
                .doesNotContain("Lee");
    }

    /**
     * 첫 번째 assert에서 실패하여 다음 assert는 수행하지 않는다.
     */
    @Test
    public void noSoftAssertionTest() {
        assertThat(members).as("크기가 일치하지 않습니다.").size().isEqualTo(1);
        assertThat(members).as("위 검증에서 멈춰 여기까지 오지 않습니다.").size().isEqualTo(2);
    }

    /**
     * 첫 번째 assert가 실패하더라도 모든 assert를 수행한다.
     */
    @Test
    public void softAssertionTest() {
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(members).as("크기가 일치하지 않습니다.").size().isEqualTo(1);
        softAssertions.assertThat(members).as("위 검증에서 실패하더라도 모든 assert를 수행합니다.").size().isEqualTo(2);
        softAssertions.assertAll();
    }

    /**
     * assertSoftly를 사용해 람다식으로 따로 assertAll을 호출할 필요 없이 사용할 수 있다.
     */
    @Test
    public void softAssertionTest_2() {
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(members).as("크기가 일치하지 않습니다.").size().isEqualTo(1);
            softAssertions.assertThat(members).as("위 검증에서 실패하더라도 모든 assert를 수행합니다.").size().isEqualTo(2);
        });
    }
}