package JPQLBasic.JPQLBasic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Rollback(value = false)
@Transactional
public class JpqlTest {

    @Autowired
    EntityManager em;

    @Test
    public void 생성() {
        Member member = new Member();
        member.setUsername("안주");
        em.persist(member);
    }

    @Test
    public void 리스트반환() {
        List<Member> memberList = em.createQuery("select m from Member m", Member.class).getResultList();
        for (Member member : memberList) {
            System.out.println(member.getUsername());
        }
    }

    @Test
    public void 타입쿼리() {
        Member singleResult = em.createQuery("SELECT m FROM Member m where m.username = :username", Member.class)
                .setParameter("username", "안주")
                .getSingleResult();

        System.out.println(singleResult.getUsername());
    }

    @Test
    public void 오브젝트() {
        List<Object[]> resultList = em.createQuery("select m.id, m.username FROM Member m").getResultList();
        Object[] result = resultList.get(0);
        System.out.println(result[0]);
        System.out.println(result[1]);
    }

    @Test
    public void 페이징_쿼리() {
        for (int i = 0; i < 100; i++) {
            Member member = new Member();
            em.persist(member);
        }
        em.flush();
        em.clear();

        List<Member> resultList = em.createQuery("SELECT m FROM Member m order by m.id ", Member.class)
                .setFirstResult(10) //11부터 시작
                .setMaxResults(50)  //60까지
                .getResultList();

        for (Member member : resultList) {
            System.out.println(member.getId());
        }
    }

    @Test
    public void 타입표현_기타식() {
        Member member = new Member();
        member.setUsername("안주");
        Member member2 = new Member();
        member2.setUsername("안주");
        em.persist(member);
        em.persist(member2);
        Team team = new Team();
        team.setName("팀1");

        Team team2 = new Team();
        team2.setName("팀2");
        em.persist(team);
        em.persist(team2);

        member.setTeam(team);
        member2.setTeam(team2);


        member.setMemberType(MemberType.ADMIN);
        member2.setMemberType(MemberType.USER);

        em.flush();
        em.clear();

//        List<Object[]> result = em.createQuery("SELECT m.id, '?', TRUE From Member m" +
//                " where m.memberType = JPQLBasic.JPQLBasic.MemberType.USER")
//                .getResultList();

        List<Object[]> result = em.createQuery("SELECT m.id, '?', TRUE From Member m" +
                        " where m.memberType = :userType")
                .setParameter("userType", MemberType.ADMIN)
                .getResultList();

        System.out.println(result.size());
        System.out.println("========================");
        for (Object[] objects : result) {
            System.out.println(objects[0]);
            System.out.println(objects[1]);
            System.out.println(objects[2]);
        }


    }

    @Test
    public void 조건식_CASE() {
        Member member = new Member();
        member.setUsername("안주형");
        member.setAge(12);

        Member member2 = new Member();
        member2.setUsername("동까");
        member2.setAge(22);

        Member member3 = new Member();
        member3.setUsername(null);
        member3.setAge(23);

        em.persist(member);
        em.persist(member2);
        em.persist(member3);
        em.flush();
        em.clear();

        List<String> resultList = em.createQuery("select case " +
                        "when m.age>=20 then '성인입니다.'" +
                        "when m.age<20 then '미성년자입니다'" +
                        "else '누구세요?'" +
                        "end " +
                        "from Member m", String.class)
                .getResultList();

        for (String s : resultList) {
            System.out.println(s);
        }

        List resultList1 = em.createQuery("select coalesce(m.username, 'username이 null인 값은 현재 이 문장이 나옴')from Member m").getResultList();
        for (Object o : resultList1) {
            System.out.println(o);
        }
    }

    @Test
    public void 페치_조인() {
        Member member = new Member();
        member.setUsername("안주형");
        em.persist(member);

        Member member2 = new Member();
        member2.setUsername("김동까");
        em.persist(member2);

        Member member3 = new Member();
        member3.setUsername("로제");
        em.persist(member3);

        Team team = new Team();
        team.setName("팀A");
        em.persist(team);

        Team team2 = new Team();
        team2.setName("팀B");
        em.persist(team2);

        Team team3 = new Team();
        team3.setName("팀C");
        em.persist(team3);

        member.setTeam(team);
        member2.setTeam(team2);
        member3.setTeam(team3);

        em.flush();
        em.clear();

        List<Team> result = em.createQuery("select t From Team t join fetch t.members", Team.class)
                .getResultList();

        for (Team teams : result) {
            System.out.println(teams.getName());
        }
    }

    @Test
    public void 네임드_쿼리() {
        Member member = new Member();
        member.setUsername("안주형");
        em.persist(member);

        Member member2 = new Member();
        member2.setUsername("제니");
        em.persist(member2);

        em.flush();
        em.clear();

        Member result = em.createNamedQuery("Member.findByUsername", Member.class)
                .setParameter("username", "안주형")
                .getSingleResult();
        System.out.println(result.getId());
    }


//    @Test
//    public void DTO_조회() {
//        List<MemberDTO> resultList = em.createQuery("select new jpql.MemberDTO(m.id, m.username) FROM Member m", MemberDTO.class).getResultList();
//        MemberDTO memberDTO = resultList.get(0);
//
//        System.out.println(memberDTO.getUsername());
//        System.out.println(memberDTO.getId());
//    }

    @Test
    public void 벌크연산() {
        Member member1 = new Member();
        member1.setUsername("안주형");
        member1.setAge(10);
        em.persist(member1);

        Member member2 = new Member();
        member2.setUsername("제니");
        member2.setAge(11);
        em.persist(member2);

        Member member3 = new Member();
        member3.setUsername("로제");
        member3.setAge(12);
        em.persist(member3);

//        em.clear();
//        em.flush();
        //flush 자동 호출 될때: commit 할때, query 나갈때, 강제로 flush 할 때
        int resultCount = em.createQuery("update Member m set m.age = 20")
                .executeUpdate();

        em.flush();
        em.clear();
        em.createQuery("update Member m set m.age=99 where m.id = 1 ").executeUpdate();

        em.clear();

        Member findMember = em.find(Member.class, member1.getId());


        System.out.println(findMember.getAge());   //10이 나옴
        System.out.println(resultCount);
    }

}
