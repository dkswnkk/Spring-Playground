package study.querydsl;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static study.querydsl.entity.QMember.*;
import static study.querydsl.entity.QMember.member;

@SpringBootTest
@Transactional(readOnly = true)
public class QuerydslBasicTest {

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    public void before() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("안주", 25, teamA);
        Member member2 = new Member("고양이", 1, teamB);

        em.persist(member1);
        em.persist(member2);

        em.flush();
        em.clear();
    }

    @Test
    public void startJPQL() {
        // 가정: 멤버 이름으로 찾기
        Member findMember = em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", "안주")
                .getSingleResult();

        assertThat(findMember.getUsername()).isEqualTo("안주");
    }

    @Test
    public void startQuerydsl() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QMember m = new QMember("m");
        QMember m2 = member;    // 이렇게도 사용 가능

        Member findMember = queryFactory
                .select(m)
                .from(m)
                .where(m.username.eq("안주"))
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("안주");

    }


    @Test
    public void search() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        Member result = queryFactory
                .selectFrom(member) //QMember.member 를 스태틱 임포트
                .where(member.username.eq("안주").and(member.age.eq(25)))
                .fetchOne();

        assertThat(result.getUsername()).isEqualTo("안주");
    }

    @Test
    public void searchAndParam() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        /*
            위랑 동일, where 절에 and로 체인을 거는 대신에 ','로도 사용 가능
         */
        Member result = queryFactory
                .selectFrom(member) //QMember.member 를 스태틱 임포트
                .where(
                        member.username.eq("안주"),
                        member.age.eq(25))
                .fetchOne();

        assertThat(result.getUsername()).isEqualTo("안주");
    }

    @Test
    public void resultFetch() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        List<Member> fetch = queryFactory
                .selectFrom(member)
                .fetch();


        /*
            단건 조회이기 때문에 한 건이 나와야 하지만 현재 두 건이 나와서 에러가 발생하는게 옳음.
         */
        assertThrows(com.querydsl.core.NonUniqueResultException.class, () -> {
            Member fetchOne = queryFactory
                    .selectFrom(member)
                    .fetchOne();
        });


        Member fetchFirst = queryFactory
                .selectFrom(member)
                .fetchFirst();  //limit(1)과 동일

        QueryResults<Member> memberQueryResults = queryFactory
                .selectFrom(member)
                .fetchResults();

        memberQueryResults.getTotal();
        List<Member> content = memberQueryResults.getResults();

    }

}
