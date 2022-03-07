package jpabasic.jpabasic.test;

import jpabasic.jpabasic.domain.Book;
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

}
