package basic.basic.discount;

import basic.basic.member.Grade;
import basic.basic.member.Member;
import org.springframework.stereotype.Component;

//@Component
public class RateDiscountPolicy implements DiscountPolicy {
    private int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {
        System.out.println("RateDiscountPolicy");
        if (member.getGrade() == Grade.VIP) {
            return price * discountPercent / 100;
        }else{
            return 0;
        }
    }
}
