package com.example.Mockito.Member;

import com.example.Mockito.domain.Member;
import com.example.Mockito.domain.Study;

import java.util.Optional;

public interface MemberService {
    Optional<Member> findById(Long memberId);

    void validate(Long memberId);

    void notify(Study newStudy);

    void notify(Member member);

}
