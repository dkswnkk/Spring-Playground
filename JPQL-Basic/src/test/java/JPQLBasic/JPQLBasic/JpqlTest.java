package JPQLBasic.JPQLBasic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Rollback(value = false)
@Transactional
public class JpqlTest {

    @Autowired
    EntityManager em;

    @Test
    public void 생성(){
        Member member = new Member();
        member.setUsername("안주");
        em.persist(member);
    }
}
