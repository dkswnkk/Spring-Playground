package study.querydsl.dto;

import lombok.Data;

@Data
public class MemberSearchCondition {
    // 회원명, 팀명, 나이(ageGoe, ageLoe)
    private String username;
    private String teamName;
    private Integer ageGoe; // 나이가 크거나 같거나
    private Integer ageLoe; // 나이가 작거나 같거나
}
