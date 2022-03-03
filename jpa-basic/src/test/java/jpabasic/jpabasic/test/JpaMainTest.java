package jpabasic.jpabasic.test;

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
class JpaMainTest {

    @Autowired
    EntityManager em;

    @Test
    public void 생성() {
        JpaMain jpaMain = new JpaMain(em);
        jpaMain.save();
    }

    @Test
    public void 찾기() {
//        JpaMain jpaMain = new JpaMain(em);
        Member findMember = em.find(Member.class, 1L);
        System.out.println(findMember.getName());
        findMember.setName("안주");
        System.out.println(findMember.getName());
    }

    @Test
    public void 쿼리써서찾기() {
        List<Member> result = em.createQuery("select m from Member as m", Member.class).getResultList();
        for (Member member : result) {
            System.out.println(member.getName());
        }
    }

    @Test
    public void 수정() {
        Member findMember = em.find(Member.class, 1L);
        findMember.setName("안주");
        System.out.println(findMember.getName());
    }
}