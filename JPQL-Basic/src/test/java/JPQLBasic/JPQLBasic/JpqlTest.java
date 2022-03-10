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

        List<Member> resultList = em.createQuery("SELECT m FROM Member m order by m.id desc ", Member.class)
                .setFirstResult(10)
                .setMaxResults(50)
                .getResultList();

        for (Member member : resultList) {
            System.out.println(member.getId());
        }
    }

//    @Test
//    public void DTO_조회() {
//        List<MemberDTO> resultList = em.createQuery("select new jpql.MemberDTO(m.id, m.username) FROM Member m", MemberDTO.class).getResultList();
//        MemberDTO memberDTO = resultList.get(0);
//
//        System.out.println(memberDTO.getUsername());
//        System.out.println(memberDTO.getId());
//    }
}
