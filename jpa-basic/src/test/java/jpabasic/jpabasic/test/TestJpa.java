package jpabasic.jpabasic.test;

import jpabasic.jpabasic.domain.Member;
import jpabasic.jpabasic.domain.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class TestJpa {

    @Autowired
    EntityManager em;

    @Test
    public void 생성() {
        Member member = new Member();
        Team team = new Team();
        em.persist(team);

        team.setName("안주팀");
        member.setName("안주형");
        member.setTeam(team);
        em.persist(member);

    }

    @Test
    public void 멤버에서조회() {
        Member findMember = em.find(Member.class, 2L);
        System.out.println(findMember.getTeam().getName());
    }

    @Test
    public void 팀에서조회() {
        Team findTeam = em.find(Team.class, 1L);
        List<Member> findTeamMembers = findTeam.getMembers();
        for (Member findTeamMember : findTeamMembers) {
            System.out.println(findTeamMember.getName());
        }
    }

    @Test
    public void 안주팀에서_동까팀으로_팀변경() {
        Member findMember = em.find(Member.class, 2L);
        Team changeTeam = em.find(Team.class, 3L);
        findMember.setTeam(changeTeam);
    }

    @Test
    public void 다른팀_생성() {
        Team team = new Team();
        team.setName("동까팀");
        em.persist(team);
    }

}
