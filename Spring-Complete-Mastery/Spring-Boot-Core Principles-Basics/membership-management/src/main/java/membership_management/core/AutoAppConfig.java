package membership_management.core;

import membership_management.core.member.MemberRepository;
import membership_management.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        //basePackages = "membership_management.core.member",
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,
               classes = Configuration.class)
)
public class AutoAppConfig {

    //자동으로 등록된 빈보다 아래와 같이 수동 으로 입력한 빈이 우선권을 갖는다.
    //Overriding bean definition for bean 'memoryMemberRepository' with a different definition: replacing
//    @Bean(name="memoryMemberRepository")
//    MemberRepository memberRepository(){
//        return new MemoryMemberRepository();
//    }

}
