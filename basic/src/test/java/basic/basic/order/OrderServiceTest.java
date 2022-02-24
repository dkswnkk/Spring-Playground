package basic.basic.order;

import basic.basic.AppConfig;
import basic.basic.AutoAppConfig;
import basic.basic.member.Grade;
import basic.basic.member.Member;
import basic.basic.member.MemberService;
import basic.basic.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderServiceTest {


    MemberService memberService;
    OrderService orderService;

//    @BeforeEach
//    public void beforeEach() {
//        AppConfig appConfig = new AppConfig();
//        memberService = appConfig.memberService();
//        orderService = appConfig.orderService();
//    }
    @BeforeEach
    public void beforeEach(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AutoAppConfig.class);
         memberService = applicationContext.getBean(MemberService.class);
         orderService = applicationContext.getBean(OrderService.class);
    }

    @Test
    void createOrder() {
        Long memberId = 1L;
        Member member = new Member(memberId, "안주형", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);

        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(2000);
    }
}
