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
    public void 초기_생성() {
        JpaMain jpaMain = new JpaMain(em);
        jpaMain.save();
    }

    @Test
    public void 새로운_아이디_생성(){
        Member member = new Member();
//        member.setName("새로운 아이디");
        member.setAge(22);
        member.setId(3L);
        em.persist(member);
    }

    @Test
    public void 찾기() {
//        JpaMain jpaMain = new JpaMain(em);
        Member findMember = em.find(Member.class, 1L);
//        System.out.println(findMember.getName());
//        findMember.setName("안주");
//        System.out.println(findMember.getName());
    }

    @Test
    public void 쿼리써서찾기() {
        List<Member> result = em.createQuery("select m from Member as m", Member.class).getResultList();
        for (Member member : result) {
//            System.out.println(member.getName());
        }
    }

    @Test
    public void 수정() {
        Member findMember = em.find(Member.class, 1L);
//        findMember.setName("안주");
//        System.out.println(findMember.getName());
    }

    @Test
    public void 영속_상태_확인() {
        Member member = new Member();
        member.setId(2L);
//        member.setName("김동까악까악");

        em.persist(member);
    }

    @Test
    public void 삭제(){
        Member member = em.find(Member.class,2L);
//        em.detach(member);
        em.remove(member);
    }

    @Test
    public void 질문_테스트(){
        Member member = new Member();
        member.setId(2L);
//        member.setName("테스트");
        em.persist(member);
//        member.setName("이걸로 바뀌나?");
    }
}