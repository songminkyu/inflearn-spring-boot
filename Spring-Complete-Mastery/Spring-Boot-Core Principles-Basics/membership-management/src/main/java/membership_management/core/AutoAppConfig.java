package membership_management.core;

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

}
