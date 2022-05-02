package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  // SpringBoot의 가장 기본적인 설정 선언.
// @Controller, @Service, @Repository 등의 Annotation 스캔 및 Bean 등록
// 사전의 정의한 라이브러리들을 Bean 등록

// Bean 간단 설명, 스프링 컨테이너가 관리하는 자바 객체
public class DemoApplication {

    public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class, args);

        // 메모리 사용량 출력
        long heapSize = Runtime.getRuntime().totalMemory();
        System.out.println("HEAP Size(M) : "+ heapSize / (1024*1024) + " MB");
    }

}
