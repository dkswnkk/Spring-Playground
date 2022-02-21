package basic.basic.order;

import basic.basic.discount.DiscountPolicy;
import basic.basic.member.Member;
import basic.basic.member.MemberRepository;

public class OrderServiceImpl implements OrderService {

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    private final MemberRepository memberRepository;
    private DiscountPolicy discountPolicy;

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
