package membership_management.core;

import membership_management.core.discount.DiscountPolicy;
import membership_management.core.discount.FixDiscountPolicy;
import membership_management.core.discount.RateDiscountPolicy;
import membership_management.core.member.Member;
import membership_management.core.member.MemberService;
import membership_management.core.member.MemberServiceImpl;
import membership_management.core.member.MemoryMemberRepository;
import membership_management.core.order.OrderService;
import membership_management.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


// C# 의 의존성 주입을 위한 ConfigurationBuild랑 비슷
// @Configuration 붙이면 바이트코드를 조작하는 CGLIB 기술을 이용해서 싱글톤을 보장해준다.

@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemoryMemberRepository memberRepository(){
        System.out.println("call AppConfig.memberRepository");
        //향후 Memory에서 DB로 변경 하기 용이
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy(){
        //향후 Memory에서 DB로 변경 하기 용이

        //return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
