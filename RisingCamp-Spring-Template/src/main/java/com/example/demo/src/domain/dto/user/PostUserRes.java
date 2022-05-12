package com.example.demo.src.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성
@AllArgsConstructor // 해당 클래스의 모든 멤버 변수(jwt, userIdx)를 받는 생성자를 생성-
public class PostUserRes {
    private Long userIdx;
    private String jwt;
}
