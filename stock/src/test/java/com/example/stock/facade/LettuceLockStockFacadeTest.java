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
 * 구현이 간단하다.
 * Spring Data Redis를 이용하면 Lettuce가 기본이기 때문에 별도의 라이브러리를 사용하지 않아도 된다.
 * Spin Lock 방식이기 때문에 동시에 많은 스레드가 Lock 획득 대기 상태라면 Redis에 부하가 갈 수 있다.
 * 실무에서는 재시도가 필요한 Lock의 경우에는 Redission을 활용하고, 그렇지 않을 경우에는 Lettuce을 활용한다.
 * 재시도가 필요한 경우?: 선착순 100명 까지 물품을 구매할 수 있을 경우
 * 재시도가 필요하지 않은 경우?: 선착순 한명만 가능, Lock 획득 재시도 할 필요가 없음
 */
@SpringBootTest
class LettuceLockStockFacadeTest {

    @Autowired
    private LettuceLockStockFacade lettuceLockStockFacade;

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
    @DisplayName("Lettuce Lock 사용")
    public void 동시에_100개_요청() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    lettuceLockStockFacade.decrease(1L, 1L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
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