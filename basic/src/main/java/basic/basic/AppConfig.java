package basic.basic;

import basic.basic.discount.DiscountPolicy;
import basic.basic.discount.FixDiscountPolicy;
import basic.basic.discount.RateDiscountPolicy;
import basic.basic.member.MemberService;
import basic.basic.member.MemberServiceImpl;
import basic.basic.member.MemoryMemberRepository;
import basic.basic.order.OrderService;
import basic.basic.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemoryMemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(new MemoryMemberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }

}
