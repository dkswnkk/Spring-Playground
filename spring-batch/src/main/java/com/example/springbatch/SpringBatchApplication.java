package com.example.springbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 총 4개의 설정 클래스를 실행시키며 스프링 배치의 모든 초기화 및 실행 구성이 이루어집니다.
 * 스프링 부트 배치의 자동 설정 클래스가 실행됨으로 빈으로 등록된 모든 Job을 검색해서 초기화와 동시에 Job을 수행하도록 구성됩니다.
 * @EnableBatchProcessing -> SimpleBatchConfiguration -> BatchConfigurerConfiguration(BasicBatchConfigurer, JpaBatchConfigurer) -> BatchAutoConfiguration
 */

/**
 * 1. BatchAutoConfiguration
 *  스프링 배치가 초기화 될 때 자동으로 실행되는 설정 클래스
 *  Job을 수행하는 JobLauncherApplicationRunner 빈읈 생성한다.
 *
 * 2. SimpleBatchConfiguration
 *  JobBuilderFactory 와 StepBuilderFactory 생성
 *  스프링 배치의 주요 구성 요소 생성 - 프록시 객체로 생성됨
 *
 * 3. BatchConfigurerConfiguration
 *  BasicBatchConfigurer
 *      SimpleBatchConfiguration에서 생성한 프록시 객체의 실제 대상 객체를 생성하는 설정 클래스
 *      빈으로 의존성 주입 받아서 주요 객체들을 참조해서 사용할 수 있다.
 *  JpaBatchConfigurer
 *      JPA 관련 객체를 생성하는 설정 클래스
 *  사용자 정의 BatchConfigurer 인터페이스를 구현하여 사용할 수 있음
 */

@SpringBootApplication
@EnableBatchProcessing
public class SpringBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchApplication.class, args);
	}

}
