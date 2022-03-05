//package jpabasic.jpabasic.test;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import java.util.List;
//
//@SpringBootTest
//@Transactional
//@Rollback(value = false)
//public class TestJpa {
//
//    @Autowired
//    EntityManager em;
//
//    @Test
//    public void 생성() {
//        Member member = new Member();
//        member.setName("동까");
//        em.persist(member);
////        em.flush();
//        System.out.println("==================");
//        Team findTeam = em.find(Team.class, 3L);
//        System.out.println("==================");
//        member.setTeam(findTeam);
//
//
//    }
//
//    @Test
//    public void 멤버에서조회() {
//        Member findMember = em.find(Member.class, 2L);
//        System.out.println(findMember.getTeam().getName());
//    }
//
//    @Test
//    public void 팀에서조회() {
//        Team findTeam = em.find(Team.class, 1L);
//        List<Member> findTeamMembers = findTeam.getMembers();
//        for (Member findTeamMember : findTeamMembers) {
//            System.out.println(findTeamMember.getName());
//        }
//    }
//
//    @Test
//    public void 안주팀에서_동까팀으로_팀변경() {
//        Member findMember = em.find(Member.class, 2L);
//        Team changeTeam = em.find(Team.class, 3L);
//        findMember.setTeam(changeTeam);
//    }
//
//    @Test
//    public void 다른팀_생성() {
//        Team team = new Team();
//        team.setName("동까팀");
//        em.persist(team);
//    }
//
//    @Test
//    public void 동까_임시조회() {
//        Team findTeam = em.find(Team.class, 3L);
//        List<Member> findTeamMembers = findTeam.getMembers();
//
//        for (Member findTeamMember : findTeamMembers) {
//            System.out.println(findTeamMember.getName());
//        }
//    }
//
//}
