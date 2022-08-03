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
        Member member3 = new Member("멍멍이", 1, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);

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

    /**
     * 회원 정렬 순서
     * 1. 회원 나이 내림차순(desc)
     * 2. 회원 이름 올림차순(asc)
     * 단 2에서 회원 이름이 같으면 마지막에 출력(null last)
     */
    @Test
    public void sort() {
        JPAQueryFactory jqf = new JPAQueryFactory(em);

        em.persist(new Member(null, 100));
        em.persist(new Member("멍멍이", 100));
        em.persist(new Member("야옹이", 100));

        List<Member> result = jqf
                .selectFrom(member)
                .where(member.age.eq(100))
                .orderBy(
                        member.age.desc(),
                        member.username.asc().nullsLast()
                )
                .fetch();

        Member 멍멍이 = result.get(0);
        Member 야옹이 = result.get(1);
        Member memberNull = result.get(2);

        assertThat(야옹이.getUsername()).isEqualTo("야옹이");
        assertThat(멍멍이.getUsername()).isEqualTo("멍멍이");
        assertThat(memberNull.getUsername()).isNull();

    }

    @Test
    public void paging1() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        List<Member> result = queryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetch();

        assertThat(result.size()).isEqualTo(2);

    }

    @Test
    public void paging2() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QueryResults<Member> result = queryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetchResults();

        assertThat(result.getTotal()).isEqualTo(3);
        assertThat(result.getLimit()).isEqualTo(2);
        assertThat(result.getOffset()).isEqualTo(1);
        assertThat(result.getResults().size()).isEqualTo(2);

    }

}
