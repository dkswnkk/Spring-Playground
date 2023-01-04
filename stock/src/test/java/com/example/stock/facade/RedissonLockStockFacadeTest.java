package com.example.stock.facade;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Lock 획득 재시도를 기본으로 제공한다.
 * pub-sub 방식으로 구현이 되어있기 때문에 Lettuce와 비교했을 때 Redis에 부하가 덜 간다.
 * 별도의 라이브러리를 사용해야 한다.
 * Lock을 라이브러리 차원에서 제공해주기 때문에 사용법을 공부해야 한다.
 * 실무에서는 재시도가 필요한 Lock의 경우에는 Redission을 활용하고, 그렇지 않을 경우에는 Lettuce을 활용한다.
 * 재시도가 필요한 경우?: 선착순 100명 까지 물품을 구매할 수 있을 경우
 * 재시도가 필요하지 않은 경우?: 선착순 한명만 가능, Lock 획득 재시도 할 필요가 없음
 */
@SpringBootTest
class RedissonLockStockFacadeTest {

    @Autowired
    private RedissonLockStockFacade redissonLockStockFacade;

    @Autowired
    private StockRepository stockRepository;

    @BeforeEach
    public void before() {
        Stock stock = new Stock(1L, 100L);
        stockRepository.saveAndFlush(stock);
    }

    @AfterEach
    public void after() {
        stockRepository.deleteAll();
    }

    @Test
    @DisplayName("Redisson Lock 사용")
    public void 동시에_100개_요청() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    redissonLockStockFacade.decrease(1L, 1L);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Stock stock = stockRepository.findById(1L).orElseThrow();

        assertEquals(0L, stock.getQuantity());

    }
}