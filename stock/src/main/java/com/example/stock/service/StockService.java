package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockService {

    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional
    public void decrease(Long id, Long quantity) {
        Stock stock = stockRepository.findById(id).orElseThrow();
        stock.decrease(quantity);
        stockRepository.saveAndFlush(stock);
    }

    /**
     * synchronized 사용시 @Transactional과 동시에 사용하면 안된다.
     * Synchronized를 사용하는 이유는 해당 메소드를 한 쓰레드에서만 돌리기 위해서다.
     * 하지만, 트랜잭션이 같이 정의가 되어있다면 첫 번째 쓰레드가 끝나기 전 두 번째 쓰레드가 발동할 수도 있다.
     */
    public synchronized void decreaseWithSynchronized(Long id, Long quantity) {
        Stock stock = stockRepository.findById(id).orElseThrow();
        stock.decrease(quantity);
        stockRepository.saveAndFlush(stock);
    }
}
