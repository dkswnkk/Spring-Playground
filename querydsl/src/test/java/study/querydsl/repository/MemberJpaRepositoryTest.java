package study.querydsl.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @PersistenceContext
    EntityManager em;
    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void basicTest() {
        Member member1 = new Member("안주형", 25);
        Member member2 = new Member("제니", 27);
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);
        Member findMember = memberJpaRepository.findById(member1.getId()).get();
        assertThat(member1.getId()).isEqualTo(findMember.getId());

        List<Member> findAll = memberJpaRepository.findAll();

        assertThat(findAll)
                .extracting("username")
                .contains("안주형", "제니");
    }

    @Test
    public void basicQuerydslTest() {
        Member member1 = new Member("안주형", 25);
        Member member2 = new Member("제니", 27);
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        List<Member> findByUsername = memberJpaRepository.findByUsername_Querydsl("안주형");

        assertThat(findByUsername.size()).isEqualTo(1);

    }

}