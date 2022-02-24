package basic.basic.discount;

import basic.basic.annotation.MainDiscountPolicy;
import basic.basic.member.Grade;
import basic.basic.member.Member;
import org.springframework.stereotype.Component;

@Component
@MainDiscountPolicy
public class FixDiscountPolicy implements DiscountPolicy {

    private int discountFixAmount = 2000;   // 2000원 할인

    @Override
    public int discount(Member member, int price) {
        System.out.println("FixDiscountPolicy");
        if (member.getGrade() == Grade.VIP) {
            return discountFixAmount;
        } else {
            return 0;
        }
    }
}
