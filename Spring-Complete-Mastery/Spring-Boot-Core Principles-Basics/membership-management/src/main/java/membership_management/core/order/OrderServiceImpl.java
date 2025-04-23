package membership_management.core.order;

import membership_management.core.discount.DiscountPolicy;
import membership_management.core.discount.FixDiscountPolicy;
import membership_management.core.member.Member;
import membership_management.core.member.MemberRepository;
import membership_management.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
