package com.example.demo.src;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration      // 스프링 설정 클래스를 선언하는 어노테이션
@EnableWebSecurity  // SpringSecurity 사용을 위한 어노테이션, 기본적으로 CSRF 활성화
// SpringSecurity란, Spring기반의 애플리케이션의 보안(인증, 권한, 인가 등)을 담당하는 Spring 하위 프레임워크
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * SpringSecurity 설정
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();  // CSRF 비활성화,
        // REST API 서버는 stateless하게 개발하기 때문에 사용자 정보를 Session에 저장 안함
        // jwt 토큰을 Cookie에 저장하지 않는다면, CSRF에 어느정도는 안전.
    }
}
