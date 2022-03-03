package jpabasic.jpabasic.test;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
public class JpaMain {
    private final EntityManager em;

    public void save() {
        Member member = new Member();
        member.setId(1L);
        member.setName("안주형");
        em.persist(member);
    }
}
