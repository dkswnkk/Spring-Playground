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
    @Mock MemberService memberService;
    @Mock StudyRepository studyRepository;

    @Test
    void createStudyService() {

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
    }

    /*
         ExtendWith 애노테이션이 있어야 사용 가능하다.
     */
    @DisplayName("Mockito.mock() 메소드로 만드는 방법")
    @Test
    void createStudyServiceWithMethod(@Mock MemberService memberService, @Mock StudyRepository studyRepository){
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
    }


}