package com.example.Mockito.study;

import com.example.Mockito.domain.Member;
import com.example.Mockito.domain.Study;
import com.example.Mockito.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {
    /*
        1. ExtendWith에 MockitoExtension.class 설정
        2. @Mock 애노테이션 설정
        => MemberService memberService = Mock(MemberService.class)와 같이 생성하지 않아도 됨.
     */
    @Mock
    MemberService memberService;
    @Mock
    StudyRepository studyRepository;

    @Test
    void createStudyService() {
        /*
            모든 Mock 객체의 행동
            1. Null을 Return한다.(Optional 타입은 Optional.empty 리턴)
            2. Primitive 타입은 기본 Primitive 값.
            3. 콜렉션은 비어있는 콜렉션.
            4. void 메소드는 예외를 던지지 않고 아무런 일도 발생하지 않는다.
         */
        StudyService studyService = new StudyService(memberService, studyRepository);
        Optional<Member> member = memberService.findById(1L);
        assert (member).isEmpty();
        memberService.validate(2L);
        assertNotNull(studyService);

        Member member2 = new Member();
        member2.setId(1L);
        member2.setEmail("test@gmail.com");
        when(memberService.findById(any())).thenReturn(Optional.of(member2));
        Study study = new Study(10, "java");
        studyService.createNewStudy(1L, study);

        Optional<Member> member3 = memberService.findById(1L);
        assertEquals("test@gmail.com", member3.get().getEmail());

        when(memberService.findById(1L)).thenThrow(new RuntimeException("이 ID는 조회할 수 없습니다."));
        assertThrows(RuntimeException.class, () -> memberService.findById(1L));

        doThrow(new IllegalArgumentException()).when(memberService).validate(3L);
        assertThrows(IllegalArgumentException.class, () -> memberService.validate(3L));
    }

    /*
         ExtendWith 애노테이션이 있어야 사용 가능하다.
     */
    @DisplayName("Mockito.mock() 메소드로 만드는 방법")
    @Test
    void createStudyServiceWithMethod(@Mock MemberService memberService, @Mock StudyRepository studyRepository) {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
    }

}