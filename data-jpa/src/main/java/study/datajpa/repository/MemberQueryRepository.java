package study.datajpa.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {

     private final EntityManager em;

     List<Member> findAllMembers(){
        return em.createQuery("select m from Member m")
                .getResultList();
     }
}
