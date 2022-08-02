package study.querydsl;

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
        QMember m2 = QMember.member;    // 이렇게도 사용 가능

        Member findMember = queryFactory
                .select(m)
                .from(m)
                .where(m.username.eq("안주"))
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("안주");

    }


}
