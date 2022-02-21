package basic.basic.order;

import basic.basic.AppConfig;
import basic.basic.member.*;

public class OrderApp {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        MemberService memberService= appConfig.memberService();
        OrderService orderService= appConfig.orderService();

        Long memberId = 1L;

        Member member = new Member(memberId, "안주형", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);

        System.out.println("주문 = " + order);
    }
}
