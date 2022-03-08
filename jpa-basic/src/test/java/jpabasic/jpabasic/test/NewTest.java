package jpabasic.jpabasic.test;

import jpabasic.jpabasic.domain.Address;
import jpabasic.jpabasic.domain.Book;
import jpabasic.jpabasic.domain.Member;
import jpabasic.jpabasic.domain.Period;
import jpabasic.jpabasic.domain.cascade.Child;
import jpabasic.jpabasic.domain.cascade.Parent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnitUtil;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class NewTest {

    @Autowired
    EntityManager em;
    @Autowired
    EntityManagerFactory emf;

    @Test
    public void 생성() {
        Book book = new Book();
        book.setName("책제목");
        book.setAuthor("안주형작가");
        em.persist(book);
    }

    @Test
    public void 비교() {
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
        System.out.println("=====================");
        Member m2 = em.getReference(Member.class, member2.getId());
        Member m3 = new Member();

        System.out.println("=====================");
        String name = m2.getName();

        System.out.println(emf.getPersistenceUnitUtil().isLoaded(m1)); //프록시 인스턴스의 초기화 여부 확인
        System.out.println(emf.getPersistenceUnitUtil().isLoaded(m2)); //프록시 인스턴스의 초기화 여부 확인
        System.out.println(emf.getPersistenceUnitUtil().isLoaded(m3)); //프록시 인스턴스의 초기화 여부 확인
    }

    @Test
    public void 영속성_전이() {
        Parent parent = new Parent();
        Child child1 = new Child();
        Child child2 = new Child();

        em.persist(parent);

        parent.addChild(child1);
        parent.addChild(child2);

        em.flush();
        em.clear();

        Parent findParent = em.find(Parent.class, parent.getId());
        findParent.getChildList().remove(0);
    }

    @Test
    public void 임베디드타입_테스트() {
        Member member = new Member();
        Address address = new Address("부산", "사하", "123");
        Address address1 = new Address("부산", "사하", "123");
//        member.setHomeAddress(address);
//        member.setWordPeriod(new Period());
//
//        Address newAddress = new Address("서울", address.getStreet(), address.getZipcode());
//        member.setHomeAddress(newAddress);
//
        em.persist(member);

        System.out.println(address.equals(address1));

    }

}
