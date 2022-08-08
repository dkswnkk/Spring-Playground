package com.example.junit5.Entity;

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
}