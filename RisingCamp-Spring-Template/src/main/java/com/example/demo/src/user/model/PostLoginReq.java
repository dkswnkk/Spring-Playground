package com.example.demo.src.user.model;

import lombok.*;

@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성
@AllArgsConstructor // 해당 클래스의 모든 멤버 변수(email, password)를 받는 생성자를 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 해당 클래스의 파라미터가 없는 생성자를 생성, 접근제한자를 PROTECTED로 설정.
/**
 * Req.java: From Client To Server
 * 로그인을 위해 서버에 전달할 데이터의 형태
 * Email, Password 정보를 전달하기 위해 Body값까지 전달하는 Post 요청을 사용한다.
 */
public class PostLoginReq {
    private String email;
    private String password;
}
