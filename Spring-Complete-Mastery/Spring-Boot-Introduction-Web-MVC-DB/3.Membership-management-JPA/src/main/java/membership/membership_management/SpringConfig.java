package membership.membership_management;
import membership.membership_management.repository.JdbcMemberRepository;
import membership.membership_management.repository.JdbcTemplateMemberRepository;
import membership.membership_management.repository.JpaMemberRepository;
import membership.membership_management.repository.MemberRepository;
import membership.membership_management.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import jakarta.persistence.EntityManager;


@Configuration
public class SpringConfig {
    private final MemberRepository memberRepository;

    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }

//   @Bean
//    public MemberRepository memberRepository(DataSource dataSource) {
//      return JpaMemberRepository(dataSource);
//      return new JdbcTemplateMemberRepository(dataSource);
 //   }
}