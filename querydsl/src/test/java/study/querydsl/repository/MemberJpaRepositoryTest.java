package study.querydsl.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.Team;

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

    @Test
    public void searchTest() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("안주", 25, teamA);
        Member member2 = new Member("고양이", 1, teamB);
        Member member3 = new Member("멍멍이", 1, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);

        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setAgeGae(25);
        condition.setAgeLoe(30);
        condition.setTeamName("teamA");

        List<MemberTeamDto> result = memberJpaRepository.searchByBuilder(condition);
        assertThat(result)
                .extracting("username")
                .containsExactly("안주");
    }


}