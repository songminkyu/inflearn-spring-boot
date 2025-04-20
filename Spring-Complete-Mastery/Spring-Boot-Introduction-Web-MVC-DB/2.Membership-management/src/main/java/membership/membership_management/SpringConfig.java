package membership.membership_management;

import membership.membership_management.repository.MemberRepository;
import membership.membership_management.repository.MemoryMemberRepository;
import membership.membership_management.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 스프링 빈 설정 클래스
 * 이 클래스는 스프링 컨테이너에 빈을 등록하기 위한 Java 설정 클래스입니다.
 * @Configuration 어노테이션을 통해 스프링 설정 클래스임을 명시합니다.
 */
@Configuration
public class SpringConfig {

    /**
     * MemberService 빈을 생성하여 스프링 컨테이너에 등록합니다.
     * memberRepository 빈을 주입받아 MemberService를 생성합니다.
     * @return MemberService 인스턴스
     */
    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    /**
     * MemberRepository 빈을 생성하여 스프링 컨테이너에 등록합니다.
     * 현재는 메모리 기반 리포지토리 구현체를 사용합니다.
     * 다른 구현체(예: JPA, JDBC)로 변경 시 이 메서드만 수정하면 됩니다.
     * @return MemberRepository 인터페이스를 구현한 인스턴스
     */
    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
}