package com.example.logtracking.trace.strategy;

import com.example.logtracking.trace.strategy.code.strategy.ContextV2;
import com.example.logtracking.trace.strategy.code.strategy.Strategy;
import com.example.logtracking.trace.strategy.code.strategy.StrategyLogic1;
import com.example.logtracking.trace.strategy.code.strategy.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV2Test {

    @Test
    @DisplayName("전략 패턴 적용")
    void strategyV1() {
        ContextV2 context = new ContextV2();
        context.execute(new StrategyLogic1());
        context.execute(new StrategyLogic2());
    }

    @Test
    @DisplayName("전략 패턴 익명 내부 클래스 적용")
    void strategyV2() {
        ContextV2 context = new ContextV2();
        context.execute(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직1 실행");
            }
        });
        context.execute(() -> log.info("비즈니스 로직 2 실행"));
    }
}
