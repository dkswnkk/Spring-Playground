package study.querydsl.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {
    @PersistenceContext
    EntityManager em;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void basicTest() {
        Member member1 = new Member("안주형", 25);
        Member member2 = new Member("제니", 27);
        memberRepository.save(member1);
        memberRepository.save(member2);
        Member findMember = memberRepository.findById(member1.getId()).get();
        assertThat(member1.getId()).isEqualTo(findMember.getId());

        List<Member> findAll = memberRepository.findAll();

        assertThat(findAll)
                .extracting("username")
                .contains("안주형", "제니");
    }
}