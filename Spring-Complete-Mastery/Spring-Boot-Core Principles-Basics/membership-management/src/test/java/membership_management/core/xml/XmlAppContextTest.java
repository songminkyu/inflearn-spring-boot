package membership_management.core.xml;

import membership_management.core.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import static org.assertj.core.api.Assertions.*;

public class XmlAppContextTest {

    //최근에는 XML 기반의 스프링 설정 정보는 잘사용 하지 않지만, 빌드를 할필요 없이 xml 파일만 교체하거나 배포에 따라
    //서비스가 유연하게 관리 할수 있는 장점이 있음,
    @Test
    void xmlAppContext() {
        ApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        assertThat(memberService).isInstanceOf(MemberService.class);
    }
}
