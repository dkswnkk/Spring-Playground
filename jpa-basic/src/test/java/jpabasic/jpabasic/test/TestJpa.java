package jpabasic.jpabasic.test;

import jpabasic.jpabasic.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class TestJpa {

    @Autowired
    EntityManager em;

    @Test
    public void 초기_생성(){
        Member member = new Member();
        em.persist(member);
    }

}
