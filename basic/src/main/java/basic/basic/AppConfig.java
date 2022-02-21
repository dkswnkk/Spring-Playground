package basic.basic;

import basic.basic.discount.FixDiscountPolicy;
import basic.basic.member.MemberService;
import basic.basic.member.MemberServiceImpl;
import basic.basic.member.MemoryMemberRepository;
import basic.basic.order.OrderService;
import basic.basic.order.OrderServiceImpl;

public class AppConfig {
    public MemberService memberService() {
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

    public OrderService orderService() {
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }

}
