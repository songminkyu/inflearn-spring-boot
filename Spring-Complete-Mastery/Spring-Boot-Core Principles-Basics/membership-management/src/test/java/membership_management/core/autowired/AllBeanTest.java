package membership_management.core.autowired;

import membership_management.core.AutoAppConfig;
import membership_management.core.discount.DiscountPolicy;
import membership_management.core.member.Grade;
import membership_management.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class AllBeanTest {

    @Test
    void findAllBean(){
        ApplicationContext ac =  new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);
        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L, "userA", Grade.VIP);
        int discountPrice = discountService.discount(member, 10000, "fixDiscountPolicy");

        assertThat(discountService).isInstanceOf(DiscountService.class);
        assertThat(discountPrice).isEqualTo(1000);

        int rateDiscountPrice = discountService.discount(member, 20000, "rateDiscountPolicy");
        assertThat(rateDiscountPrice).isEqualTo(2000);
    }

    @Service
    static class DiscountService {
        private final Map<String, DiscountPolicy> PolicyMap;
        private final List<DiscountPolicy> PolicyList;

        @Autowired
        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policyList) {
            PolicyMap = policyMap;
            PolicyList = policyList;
            System.out.println("PolicyMap = " + PolicyMap);
            System.out.println("PolicyList = " + PolicyList);
        }

        public int discount(Member member, int price, String discountCode) {
            DiscountPolicy discountPolicy = PolicyMap.get(discountCode);
            return discountPolicy.discount(member,price);

        }
    }

}
