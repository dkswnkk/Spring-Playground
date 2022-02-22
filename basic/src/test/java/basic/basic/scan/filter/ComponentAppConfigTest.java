package basic.basic.scan.filter;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

public class ComponentAppConfigTest {

    @Test
    void filterScan() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);
        BeanA beanA = applicationContext.getBean("beanA", BeanA.class);
        Assertions.assertThat(beanA).isNotNull();
    }

    @Configuration
    @ComponentScan(
            includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = MyIncludeComponent.class),
            excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class)
    )
    static class ComponentFilterAppConfig {
    }
}
