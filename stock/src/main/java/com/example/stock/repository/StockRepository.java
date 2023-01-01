package com.example.stock.repository;

import com.example.stock.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;

/**
 * Database 를 활용하여 레이스컨디션 해결해보기
 *
 * Optimistic Lock
     * lock 을 걸지않고 문제가 발생할 때 처리합니다.
     * 대표적으로 version column 을 만들어서 해결하는 방법이 있습니다.
 *
 * Pessimistic Lock (exclusive lock)
     * 다른 트랜잭션이 특정 row 의 lock 을 얻는것을 방지합니다.
         * A 트랜잭션이 끝날때까지 기다렸다가 B 트랜잭션이 lock 을 획득합니다.
     * 특정 row 를 update 하거나 delete 할 수 있습니다.
     * 일반 select 는 별다른 lock 이 없기때문에 조회는 가능합니다.
 *
 * named Lock 활용하기
     * 이름과 함께 lock 을획득합니다. 해당 lock 은 다른세션에서 획득 및 해제가 불가능합니다.
 */
public interface StockRepository extends JpaRepository<Stock, Long> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from Stock s where s.id = :id")
    Stock findByIdWithPessimisticLock(Long id);
}
