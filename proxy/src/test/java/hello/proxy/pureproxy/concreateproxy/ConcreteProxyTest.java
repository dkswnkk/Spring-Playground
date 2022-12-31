package hello.proxy.pureproxy.concreateproxy;

import hello.proxy.pureproxy.concreateproxy.code.ConcreteClient;
import hello.proxy.pureproxy.concreateproxy.code.ConcreteLogic;
import hello.proxy.pureproxy.concreateproxy.code.TimeProxy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 지금까지 인터페이스를 기반으로 프록시를 도입했다.
 * 그런데 자바의 다형성은 인터페이스를 구현하든, 아니면 클래스를 상속하든 상위 타입만 맞으면 다형성이 적용된다.
 * 쉽게 이야기해서 인터페이스가 없어도 프록시를 만들 수 있다는 뜻이다.
 * 그래서 이 테스트는 인터페이스가 아니라 클래스를 기반으로 상속을 받아서 프록시를 만드는 테스트이다.
 */
public class ConcreteProxyTest {

    @Test
    @DisplayName("no proxy test")
    void noProxy() {
        ConcreteLogic concreteLogic = new ConcreteLogic();
        ConcreteClient client = new ConcreteClient(concreteLogic);
        client.execute();
    }

    /**
     * 여기서 핵심은 ConcreteClient의 생성자에 concreteLogic이 아니라 timeProxy를 주입하는 부분이다.
     * ConcreteClient는 ConcreteLogic을 의존하는데, 다형성에 의해 ConcreteLogic에 concreteLogic도 들어갈 수 있고, timeProxy도 들어갈 수 있다.
     * 즉, 인터페이스가 없어도 프록시를 도입할 수 있다.
     */
    @Test
    @DisplayName("proxy test")
    void addProxy() {
        ConcreteLogic concreteLogic = new ConcreteLogic();
        TimeProxy timeProxy = new TimeProxy(concreteLogic);
        ConcreteClient client = new ConcreteClient(timeProxy);
        client.execute();
    }
}
