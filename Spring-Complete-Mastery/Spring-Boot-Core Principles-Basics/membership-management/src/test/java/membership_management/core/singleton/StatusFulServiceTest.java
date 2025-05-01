package membership_management.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatusFulServiceTest {

    @Test
    void statusFulServiceSingletonTest(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatusFulService statusFulService1 = ac.getBean("statusFulService", StatusFulService.class);
        StatusFulService statusFulService2 = ac.getBean("statusFulService", StatusFulService.class);

        //ThreadA : 사용자 A 가 10000원 주문
        statusFulService1.order("userA", 10000);

        //ThreadB : 사용자 B 가 20000원 주문
        statusFulService2.order("userB", 20000);

        //ThreadA : 사용자 A의 주문 금액 조회
        int price = statusFulService1.getPrice();
        System.out.println("price = " + price);

        Assertions.assertThat(statusFulService1.getPrice()).isEqualTo(20000);
    }

    @Test
    @DisplayName("필드 분리")
    void statusFulServiceFieldTest(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatusFulService statusFulService1 = ac.getBean("statusFulService", StatusFulService.class);
        StatusFulService statusFulService2 = ac.getBean("statusFulService", StatusFulService.class);

        //ThreadA : 사용자 A 가 10000원 주문
        int userAprice = statusFulService1.renew_order("userA", 10000);

        //ThreadB : 사용자 B 가 20000원 주문
        int userBprice = statusFulService2.renew_order("userB", 20000);

        //각각의 주문 금액 조회
        System.out.println("userAprice = " + userAprice);
        System.out.println("userBprice = " + userBprice);

        Assertions.assertThat(userAprice).isNotSameAs(userBprice);
    }

    static class TestConfig{
        @Bean
        public StatusFulService statusFulService(){
            return new StatusFulService();
        }
    }

}