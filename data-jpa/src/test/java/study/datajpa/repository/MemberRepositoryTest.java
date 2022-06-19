package study.datajpa.repository;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional(readOnly = false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void test() {
        Member member = new Member("안주형");
        Member savedMember = memberRepository.save(member);

        Member byId = memberRepository.findById(savedMember.getId()).get();
        assertThat(member).isEqualTo(savedMember);

        assertThat(member.getUsername()).isEqualTo(byId.getUsername());
    }

    @Test
    public void findByUsernameAndAgeGreaterThen() {
        Member m1 = new Member("안주형", 25);
        Member m2 = new Member("제니", 26);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsernameAndAgeIsGreaterThan("안주형", 20);
        System.out.println("=======> " + result.get(0).getUsername());

        assertThat(result.get(0).getUsername()).isEqualTo(m1.getUsername());
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getUsername()).isNotEqualTo(m2.getUsername());
    }

    @Test
    public void testQuery() {
        Member m1 = new Member("안주형", 25);
        Member m2 = new Member("제니", 26);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findUser("제니", 26);
        assertThat(result.get(0)).isEqualTo(m2);
    }
}