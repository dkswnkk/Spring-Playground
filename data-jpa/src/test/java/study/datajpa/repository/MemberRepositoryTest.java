package study.datajpa.repository;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

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
}