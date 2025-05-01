package membership_management.core.discount;

import membership_management.core.member.Grade;
import membership_management.core.member.Member;
import org.springframework.stereotype.Component;

@Component
public class RateDiscountPolicy implements DiscountPolicy {

    private int discountpercent = 10;
    @Override
    public int discount(Member member, int price) {
       if(member.getGrade() == Grade.VIP){
            return price * discountpercent / 100;
       }
       else
       {
            return 0;
       }
    }
}
