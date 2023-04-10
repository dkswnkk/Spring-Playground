package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Autowired
//    UserDetailsService userDetailsService;

    @Bean
    public UserDetailsManager users() {
        UserDetails user = User.builder()
                .username("user")
                .password("{noop}u1234")
                .roles("USER")
                .build();

        UserDetails sys = User.builder()
                .username("sys")
                .password("{noop}s1234")
                .roles("SYS")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}a1234")
                .roles("ADMIN", "SYS", "USER")
                .build();

        return new InMemoryUserDetailsManager(user, sys, admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/user").hasRole("USER")
                .antMatchers("/admin/pay").hasRole("ADMIN")
                .antMatchers("/admin/**").access("hasRole('ADMIN') or hasRole('SYS')")
                .anyRequest()
                .authenticated();

        http.csrf()
                .disable();

        http.formLogin()
//                .loginPage("/loginPage")    // 사용자 정의 로그인 페이지
                .defaultSuccessUrl("/")     // 로그인 성공 후 이동 페이지
                .failureUrl("/login")       // 로그인 실패 후 이동 페이지
                .usernameParameter("userId")    // 아이디 파라미터명 설정
                .passwordParameter("pwd")   // 패스워드 파라미터명 설정
//                .loginProcessingUrl("/login_proc")  // 로그인 Form Action Url
                .successHandler((request, response, authentication) -> {    // 로그인 성공 후 핸들러
                    RequestCache requestCache = new HttpSessionRequestCache();
                    SavedRequest savedRequest = requestCache.getRequest(request, response); // 원래 사용자가 요청했던 request 정보가 저장되어 있음
                    String redirectUrl = savedRequest.getRedirectUrl();
                    response.sendRedirect(redirectUrl);
                })
                .failureHandler((request, response, exception) -> {     // 로그인 실패 후 핸들러
                    System.out.println("exception" + exception.getMessage());
                    response.sendRedirect("/login");
                })
                .permitAll();

        http.logout()   // 로그아웃 처리
                .logoutUrl("/logout")      //  로그아웃처리 URL
                .logoutSuccessUrl("/login") // 로그아웃 성공 후 이동페이지
                .addLogoutHandler(  // 로그아웃 핸들러
                        new LogoutHandler() {
                            @Override
                            public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                                HttpSession session = request.getSession();
                                session.invalidate();
                            }
                        }
                )
                .logoutSuccessHandler(  // 로그아웃 성공 후 핸들러
                        new LogoutSuccessHandler() {
                            @Override
                            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                                response.sendRedirect("/login");
                            }
                        }
                )
                .deleteCookies("remember");

        http.rememberMe()   // rememberMe 기능이 작동함
                .rememberMeParameter("remember")    // default: remember-me
                .tokenValiditySeconds(3600); // default: 14일
//                .userDetailsService(userDetailsService);

        http.sessionManagement()
                .sessionFixation().changeSessionId()    // 기본값, 이 외에 none, migrateSession(이전 세션의 여러 속성 값을 그대로 사용 O), newSession(이전 세션의 여러 속성 값을 그대로 사용 X) 이 있음
                .maximumSessions(2)     // 최대 허용 가능 세션 수, 무제한(-1)
                .maxSessionsPreventsLogin(true);     // 동시 로그인 차단함, default: false(이 경우, 기존 로그인 된 세션 만료시킴), 최대 허용 가능 세션 수를 넘어섰을때 로그인 시 차단

        /*
            ALWAYS: 스프링 시큐리티가 항상 세션을 생성
            IF_REQUIRED: 스프링 시큐리티가 필요 시 생성(default)
            NEVER: 스프링 시큐리티가 생성하지 않지만 이미 존재하면 사용
            STATELESS: 스프링 시큐리티가 생성하지 않고 존재해도 사용하지 않음
         */
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        http.exceptionHandling()
                .authenticationEntryPoint(      // 인증 실패 시 처리
                        new AuthenticationEntryPoint() {
                            @Override
                            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                                response.sendRedirect("/login");
                            }
                        }
                )
                .accessDeniedHandler(        // 인가 실패 시 처리
                        new AccessDeniedHandler() {
                            @Override
                            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                                response.sendRedirect("/denied");
                            }
                        }
                );

        return http.build();
    }
}
