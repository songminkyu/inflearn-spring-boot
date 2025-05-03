package membership_management.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.assertThat;

public class ProtoTypeTest {

    @Test
    void protoTypeBeanTest(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        System.out.println("find PrototypeBean1");
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        System.out.println("find PrototypeBean2");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);

        System.out.println("PrototypeBean1 = " + prototypeBean1);
        System.out.println("PrototypeBean2 = " + prototypeBean2);

        assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

        //호출 해도 prototype 이다보니 destroy() 함수는 호출 안됨.
        ac.close();
    }
    @Scope("prototype")
    static class PrototypeBean {
        @PostConstruct
        public void init(){
            System.out.println("PrototypeBean init");
        }

        /*
         ac.close() 호출 해도 prototype 이다보니 destroy 호출이 안되고, 오직
         PostConstruct 로부터 초기화부분에대한 의존관계주입까지만 관리하게 된다.
        */
        @PreDestroy
        public void destroy(){
            System.out.println("PrototypeBean destroy");
        }
    }
}
