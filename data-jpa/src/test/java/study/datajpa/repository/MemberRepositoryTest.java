package study.datajpa.repository;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional(readOnly = false)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TeamRepository teamRepository;
    @PersistenceContext
    private EntityManager em;

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

    @Test
    public void testQuery2() {
        Member m1 = new Member("안주형", 25);
        Member m2 = new Member("제니", 26);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<String> result = memberRepository.findUsernameList();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0)).isEqualTo(m1.getUsername());
    }

    @Test
    public void testQuery3() {
        Member m1 = new Member("안주형", 25);
        Team team1 = new Team("팀1");
        m1.changeTeam(team1);
        teamRepository.save(team1);
        memberRepository.save(m1);

        List<MemberDto> result = memberRepository.findMemberDto();
        for (MemberDto memberDto : result) {
            System.out.println(memberDto);
        }
    }

    @Test
    public void testQuery4() {
        Member m1 = new Member("안주형", 25);
        Member m2 = new Member("제니", 26);
        Member m3 = new Member("사람", 25);

        memberRepository.save(m1);
        memberRepository.save(m2);
        memberRepository.save(m3);


        List<Member> result = memberRepository.findByNames(Arrays.asList("안주형", "제니"));
        for (Member member : result) {
            System.out.println(member);
        }
    }

    @Test
    public void 리턴타입_테스트() {
        Member m1 = new Member("안주형", 25);
        memberRepository.save(m1);

        Member result1 = memberRepository.findMemberByUsername("안주형");
        List<Member> result2 = memberRepository.findListByUsername("안주형");
        Optional<Member> result3 = memberRepository.findOptionalByUsername("안주형");
        Optional<Member> result4 = Optional.empty();

        System.out.println(result1);
        System.out.println(result2.get(0));
        System.out.println(result3.orElseGet(() -> new Member("안주", 25)));
        System.out.println(result4.orElseGet(() -> new Member("빈값", 0)));
    }


    @Test
    public void 페이징_테스트() {
        //given
        memberRepository.save(new Member("멤버1", 25));
        memberRepository.save(new Member("멤버2", 25));
        memberRepository.save(new Member("멤버3", 25));
        memberRepository.save(new Member("멤버4", 25));
        memberRepository.save(new Member("멤버5", 25));

//        int age = 25;
//        int offset = 0;
//        int limit = 3;
        int age = 25;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.ASC, "username"));

        //when
        Page<Member> page = memberRepository.findByAge(age, pageRequest);

        Page<MemberDto> memberDtos = page.map(member -> new MemberDto(member.getId(), member.getUsername(), null)); // dto로 변경

        Slice<Member> page2 = memberRepository.findByAge(age, pageRequest); // 전체 갯수를 가져오지 않음.

        //then
        List<Member> content = page.getContent();
        long totalElements = page.getTotalElements();

        for (Member member : content) {
            System.out.println(member);
        }

        System.out.println(totalElements);
        assertThat(page.getContent().size()).isEqualTo(3);    // 현재 불러온 갯수
        assertThat(page.getTotalElements()).isEqualTo(5);   //총 갯수
        assertThat(page.getNumber()).isEqualTo(0);  // 현재 페이지 넘버
        assertThat(page.getTotalPages()).isEqualTo(2);  // 총 페이지 갯수 3, 2 해서 두개
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();

    }

    @Test
    public void 벌크_테스트() {
        //given
        memberRepository.save(new Member("멤버1", 25));
        memberRepository.save(new Member("멤버2", 26));
        memberRepository.save(new Member("멤버3", 27));
        memberRepository.save(new Member("멤버4", 28));
        memberRepository.save(new Member("멤버5", 29));

        //when
        //벌크 연산 같은 경우 영속성 캐시에 상관없이 DB에 직접적으로 업데이트함
        //따라서 벌크 연산 후에는 영속성 캐시를 전부 날려버려야함.
        int updateCount = memberRepository.bulkAgePlus(10);
       // em.clear(); // 영속성 캐시를 전부 날림. @Modifying(clearAutomatically = true) 쓰면 따로 적어주지 않아도 된다.

        Optional<Member> findMember = memberRepository.findOptionalByUsername("멤버5");
        System.out.println(findMember.orElseGet(()->null));
        assertThat(updateCount).isEqualTo(5);

    }
}
