package membership_management.core.autowired;
import membership_management.core.member.Member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void AutowiredOption(){
        // TestBean을 직접 스프링 빈으로 등록
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }


    // 스프링 빈으로 등록되도록 @Component 추가
    @Component
    static class TestBean{

        // Member 빈이 없을 때 메서드 자체가 호출되지 않음
        @Autowired(required = false)
        public void setNoBean1(Member noBean1){
            System.out.println("noBean1 = " + noBean1);
        }

        // Member 빈이 없을 때 null로 주입됨
        @Autowired
        public void setNoBean2(@Nullable Member noBean2){
            System.out.println("noBean2 = " + noBean2);
        }

        // Member 빈이 없을 때 Optional.empty로 주입됨
        @Autowired(required = false)
        public void setNoBean3(Optional<Member> noBean3){
            System.out.println("noBean3 = " + noBean3);
        }
    }
}
