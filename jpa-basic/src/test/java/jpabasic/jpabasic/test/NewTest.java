package jpabasic.jpabasic.test;

import jpabasic.jpabasic.domain.Book;
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
public class NewTest {

    @Autowired
    EntityManager em;

    @Test
    public void 생성(){
        Book book = new Book();
        book.setName("책제목");
        book.setAuthor("안주형작가");
        em.persist(book);
    }

    @Test
    public void 비교(){
        Member member1 = new Member();
        member1.setName("안주형");
        System.out.println("======================");
        System.out.println(member1.getId());
        em.persist(member1);
        System.out.println("====================");
        System.out.println(member1.getId());

        Member member2 = new Member();
        member2.setName("김동까");
        em.persist(member2);

        em.flush();
        em.clear();

        Member m1 = em.find(Member.class, member1.getId());
        Member m2 = em.find(Member.class, member2.getId());

        System.out.println(m1);
        System.out.println(m2);

        System.out.println(m1.getClass());
        System.out.println(m2.getClass());
    }

}
