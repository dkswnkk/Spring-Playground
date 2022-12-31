package hello.proxy.pureproxy.concreateproxy.code;

public class ConcreteClient {

    private ConcreteLogic concreteLogic;

    public ConcreteClient(ConcreteLogic concreteLogic) {
        this.concreteLogic = concreteLogic; // ConcreteLogic, TimeProxy 모두 주입 가능
    }

    public void execute() {
        concreteLogic.operation();
    }
}
