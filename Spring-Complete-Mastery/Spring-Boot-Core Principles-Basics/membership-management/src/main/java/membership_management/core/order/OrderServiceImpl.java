package membership_management.core.order;

import membership_management.core.discount.DiscountPolicy;
import membership_management.core.member.Member;
import membership_management.core.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

    //ocp/DIP 위반 -> 구체가 바뀌면 클라이언트 코드가 변경 되기때문에
    //private final MemberRepository memberRepository = new MemoryMemberRepository();
    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    //private final DiscountPolicy discountPolicy = new RateDiscountPolicy();


    //DIP 위반 아님(구현체를 직접 의존 않고 인터페이스에 의존 할수 있도록 변경)
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    //Autowired를 사용하면 생성자에서 여러 의존관계도 한번에 주입받을 수 있다.
    //생성자가 1개 일때는 Autowired 생략 가능, 이유는 Autowired 암시적으로 적용 되어 있음
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    //테스트용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
