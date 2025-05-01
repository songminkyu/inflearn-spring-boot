package membership_management.core.order;

import membership_management.core.AppConfig;
import membership_management.core.member.Grade;
import membership_management.core.member.Member;
import membership_management.core.member.MemberService;
import membership_management.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderServiceTest {
    MemberService memberService;
    OrderService orderService;

    @BeforeEach
    public void beforeEach(){
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        memberService = context.getBean("memberService",MemberService.class);
        orderService = context.getBean("orderService",OrderService.class);
    }
    @Test
    void createOrder(){
        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 1000);
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(100);
    }
}
