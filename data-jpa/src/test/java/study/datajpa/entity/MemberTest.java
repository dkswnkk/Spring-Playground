package study.datajpa.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void testEntity() {
        Team team1 = new Team("1팀");
        Team team2 = new Team("2팀");
        em.persist(team1);
        em.persist(team2);
        Member member1 = new Member("안주형", 25, team1);
        Member member2 = new Member("제니", 25, team1);
        Member member3 = new Member("로제", 25, team2);
        Member member4 = new Member("지수", 25, team2);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
        em.flush();
        em.clear();


        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        for (Member member : members) {
            System.out.println(member);
            System.out.println(member.getTeam());
        }
    }
}