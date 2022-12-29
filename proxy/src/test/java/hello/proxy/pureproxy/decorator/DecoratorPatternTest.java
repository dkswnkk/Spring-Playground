package hello.proxy.pureproxy.decorator;

import hello.proxy.pureproxy.decorator.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class DecoratorPatternTest {

    @Test
    @DisplayName("no decorator test")
    void noDecorator() {
        Component realComponent = new RealComponent();
        DecoratorPatternClient client = new DecoratorPatternClient(realComponent);
        client.execute();
    }

    /**
     * 실행 결과를 보면 MessageDecorator가 RealComponent를 호출하고 반환한 응답 메시지를 꾸며서 반환한 것을 확인할 수 있다.
     */
    @Test
    @DisplayName("MessageDecorator test")
    void decorator1() {
        Component realComponent = new RealComponent();
        Component messageDecorator = new MessageDecorator(realComponent);
        DecoratorPatternClient client = new DecoratorPatternClient(messageDecorator);
        client.execute();
    }

    @Test
    @DisplayName("MessageDecorator and TimeDecorator test")
    void decorator2() {
        Component realComponent = new RealComponent();
        Decorator messageDecorator = new MessageDecorator(realComponent);
        Decorator timeDecorator = new TimeDecorator(messageDecorator);
        DecoratorPatternClient client = new DecoratorPatternClient(timeDecorator);
        client.execute();
    }
}
