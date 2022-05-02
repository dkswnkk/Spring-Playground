package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성
@AllArgsConstructor // 해당 클래스의 모든 멤버 변수(jwt, userIdx)를 받는 생성자를 생성-
/**
 * Res.java: From Server To Client
 * 회원가입의 결과(Respone)를 보여주는 데이터의 형태
 */
public class PostUserRes {
    private int userIdx;
//    해당 부분은 7주차 - JWT 수업 후 주석해제 및 대체해주세요!
//    private String jwt;
}
