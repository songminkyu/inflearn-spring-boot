package membership_management.core.singleton;

import membership_management.core.AppConfig;
import membership_management.core.member.MemberRepository;
import membership_management.core.member.MemberServiceImpl;
import membership_management.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSigletonTest {

    @Test
    void configurationTest(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        System.out.println("memberService -> memberRepository1 = " + memberRepository1);
        System.out.println("orderService -> memberRepository2 = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);

        //모두 같은 인스턴스를 참고 하고 있다.
        Assertions.assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        Assertions.assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);

        /* 출력 예시
         memberService -> memberRepository1 = membership_management.core.member.MemoryMemberRepository@8ab78bc
         orderService -> memberRepository2 = membership_management.core.member.MemoryMemberRepository@8ab78bc
         memberRepository = membership_management.core.member.MemoryMemberRepository@8ab78bc
         */

        /*
        스프링컨테이너에서 싱글톤을 관리 해주기 떄문에 1개식 출력 됨
        call AppConfig.memberService
        call AppConfig.memberRepository
        call AppConfig.orderService
        */
    }

    @Test
    void configurationDeep() {
        //김영한 @Configuration과 바이트코드 조작의 마법 강의 참고
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean = " + bean.getClass());
        /*
            출력 예시)

            아래는 개발자가 만든 클래스가 아니고 스프링컨테이네쪽에서
            CGLIB 라는 바이트코드 조작라이브러리를 이용해서 AppConfig 클래스를 상속 받은 임의의
            다른클래스를 만들고, 그 다른 클래스를 스프링 빈으로 등록 한것이다.
            bean = class membership_management.core.AppConfig$$SpringCGLIB$$0
        */

        /*
            AppConfig@CGLIB 예상 동작 시나리오 코드
            @Bean
            public MemberRepository memberRepository(){
                if(memoryMemberRepository가 이미 스프링 컨테이너에 등록 되어 있으면?){
                    return 스프링 컨테이너에서 찾아서 반환
                }
                else { //스프링이 컨테이너 없다면?
                    기존 로직을 호출 해서 MemoryMemberRepository를 생성 하고 스프링 컨테이너에
                    등록

                    return 반환
                }
            }
       */

    }
}
