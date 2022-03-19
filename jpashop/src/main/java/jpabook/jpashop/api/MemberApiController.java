package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.name);
        Long id = memberService.join(member);
        return new CreateMemberResponse(member.getId());
    }

    @GetMapping("api/v2/members")
    public Result membersV2() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getId(), m.getName()))
                .collect(Collectors.toList());
        return new Result(collect.size(), collect);
    }


    @GetMapping("api/v1/members")
    public FindMember findMember(@RequestParam @Valid Long memberId) {
        Member findMember = memberService.findOne(memberId);
        return new FindMember(findMember);
    }

    @PutMapping("api/v2/members/{id}")
    public UpdateMemberResponse updateMemberResponse(@PathVariable("id") Long id,
                                                     @RequestBody @Valid UpdateMemberRequest updateMemberRequest) {
        memberService.update(id, updateMemberRequest.getName());
        Member member = memberService.findOne(id);
        return new UpdateMemberResponse(member.getName());

    }


    @Data
    @AllArgsConstructor
    static class Result<T> {
        private long count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private long id;
        private String name;
    }


    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    static class UpdateMemberResponse {
        private String name;
        private String text = "성공적으로 업데이트 했습니다.";

        public UpdateMemberResponse(String name) {
            this.name = name;
        }
    }

    @Data
    static class CreateMemberRequest {
        private String name;
        private String test;
    }

    @Data
    static class FindMember {
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
