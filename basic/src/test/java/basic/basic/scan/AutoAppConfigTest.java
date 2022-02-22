package basic.basic.scan;

import basic.basic.AutoAppConfig;
import basic.basic.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class AutoAppConfigTest {
    @Test
    void basicScan() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AutoAppConfig.class);

        MemberService bean = applicationContext.getBean(MemberService.class);
        assertThat(bean).isInstanceOf(MemberService.class);

    }
}
