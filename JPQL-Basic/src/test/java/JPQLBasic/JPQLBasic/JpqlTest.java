package JPQLBasic.JPQLBasic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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
}
