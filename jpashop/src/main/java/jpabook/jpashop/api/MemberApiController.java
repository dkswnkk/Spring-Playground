package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){
        Member member = new Member();
        member.setName(request.name);
        Long id = memberService.join(member);
        return new CreateMemberResponse(member.getId());
    }

    @GetMapping("api/v1/members")
    public FindMember findMember(@RequestParam @Valid Long memberId){
        Member findMember = memberService.findOne(memberId);
        return new FindMember(findMember);
    }

    @Data
    public class CreateMemberRequest{
        private String name;
        private String test;
    }

    @Data
    static class FindMember{
        private Member member;

        public FindMember(Member member) {
            this.member = member;
        }
    }

    @Data
    static class CreateMemberResponse {
        private Long id;
        private String test;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

}
