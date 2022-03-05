package jpabasic.jpabasic.test;

import jpabasic.jpabasic.domain.Member;
import jpabasic.jpabasic.domain.Order;
import jpabasic.jpabasic.domain.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class NewTest {

    @Autowired
    EntityManager em;

    @Test
    public void 생성(){
        Order order = new Order();
        OrderItem orderItem = new OrderItem();
        em.persist(order);
        orderItem.setOrder(order);
        em.persist(orderItem);
    }

}
