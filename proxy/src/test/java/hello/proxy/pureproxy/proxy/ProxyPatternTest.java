package hello.proxy.pureproxy.proxy;

import hello.proxy.pureproxy.proxy.code.CacheProxy;
import hello.proxy.pureproxy.proxy.code.ProxyPatternClient;
import hello.proxy.pureproxy.proxy.code.RealSubject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProxyPatternTest {

    /**
     * client.execute()를 총 3번 호출한다. 데이터를 조회하는데 1초가 소모되므로, 총 3초의 시간이 걸린다.
     * 그런데 이 데이터가 한번 조회하면 변하지 않는 데이터라면 어딘가에 보관해두고 이미 조회한 데이터를 사용하는 것이 성능상 좋다.
     * 이런 것을 캐시라고 한다.
     * 프록시 패턴의 주요 기능은 접근 제어이다. 캐시도 접근 자체를 제어하는 기능 중 하나이다.
     */
    @Test
    @DisplayName("no proxy test")
    void noProxyTest() {
        RealSubject realSubject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(realSubject);
        client.execute();
        client.execute();
        client.execute();
    }

    /**
     * cacheProxyTest()는 client.execute()를 총 3번 호출한다.
     * 이번에는 클라이언트가 실제 realSubject를 호출하는 것이 아니라 cacheProxy를 호출하게 된다.
     */
    @Test
    @DisplayName("cache proxy test")
    void cacheProxyTest() {
        RealSubject realSubject = new RealSubject();
        CacheProxy cacheProxy = new CacheProxy(realSubject);
        ProxyPatternClient client = new ProxyPatternClient(cacheProxy);
        client.execute();
        client.execute();
        client.execute();
    }
}
