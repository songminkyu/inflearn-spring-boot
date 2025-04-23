package membership_management.core.discount;
import membership_management.core.member.Member;

public interface DiscountPolicy{
    int discount(Member member, int price);
}
