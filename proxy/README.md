# 스프링 핵심 원리 - 고급편 

## 강의 목적
스프링의 핵심 원리와 고급 기술들을 깊이 있게 학습하고 사용
- 스프링의 핵심
    - Thread Local
    - Design Pattern
    - Aspect-Oriented  Programming

## 1. Thread local

동시성 문제란 Thread Safe 하지 않게 개발된 어플리케이션에서 동시에 여러 request 가 발생했을 때 여러 쓰레드가 동시에 어플리케이션 로직을 호출할 때 발생한다.</br>
여러 쓰레드가 동시에 어플리케이션 로직을 호출하게 되면 의도하지 않은 방향으로 로직이 호출되게 되는데, 여기서 의도하지 않은 방향이란 로직의 순서가 꼬일 수 있고, 이로 인해 발생하는 문제는 예측이 불가능하다.</br>
대표적으로 스프링에서 Bean 등록을 하게 되면 default 로 Singleton Pattern 등록이 되는데, 이를 동시에 request 하였을 때 발생하거나 static 같은 공용 필드에 접근하였을 때 발생한다.

> 동시성 문제가 발생하지 않는 경우
> - 지역 변수에서는 동시성 문제가 발생하지 않는다. (지연 변수는 쓰레드마다 각각 다른 메모리 영역이 할당 된다.)
> - 값을 읽기만 하면 발생하지 않는다.

이와 같은 동시성 문제를 해결하기 위한 방법이 ThreadLocal 이다.

### **사용목적**

ThreadLocal 은 해당 쓰레드만 접근할 수 있는 특별한 저장소를 말한다.</br>
아래는 일반적인 변수 필드에 접근하는 경우의 이미지이다.

<img src="https://user-images.githubusercontent.com/74492426/209663689-84204cae-0c7d-46cb-a100-ef4761f21350.png" width="500px">

위와 같이 여러 쓰레드가 하나의 변수 필드에 동시에 접근하게 되면 처음 쓰레드가 보관한 데이터는 사라질 수 있다.</br>
이와 반대로 ThreadLocal 을 사용하면 아래와 같이 저장소가 생성 된다.

<img src="https://user-images.githubusercontent.com/74492426/209663761-7c26708a-0f1b-4859-b1e1-36037ce83617.png" width="500px">

ThreadLocal 은 각 쓰레드마다 별도의 내부 저장소를 제공한다. 그러므로 여러 쓰레드가 하나의 변수에 동시에 접근하는 경우는 사라지게 된다.

### **사용방법**

```
ThreadLocal 클래스를 이용하면 된다.

Ex.
ThreadLocal<String> str = new ThreadLocal<>();
```

### **주의사항**

ThreadLocal 을 모두 사용하였다면  ThreadLocal.remove() 를 통해 쓰레드 로컬에 저장된 값을 제거해야 한다.

- 삭제하는 이유는 WAS(톰켓) 처럼 쓰레드 풀을 사용하는 경우에 여러 쓰레드를 자원의 효율성을 고려하여 재사용하게 되는데, 과거에 사용하였던 쓰레드가 중복으로 호출 될 수가 있기 때문이다.

## 2. Design Pattern

- Template Method Pattern
- Strategy Pattern
- Template CallBack Pattern
- Proxy Patternf
- Decorator Pattern

### **Template Method Pattern**

부모 클래스에서 알고리즘의 골격인 템플릿을 정의하고, 일부 변경되는 로직은 자식 클래스에 정의하는 것

<img src="https://user-images.githubusercontent.com/74492426/209663797-dd0c9cf1-9f5e-46ad-83b5-bd97d44e3dd5.png" width="500px">

이렇게 Template Method Pattern 을 사용하게되면 자식 클래스가 알고리즘 전체 구조를 변경하지 않고 특정 부분만 재정의 할 수 있다.</br>
즉,  상속과 오버라이딩을 통한 다형성으로 문제를 해결하는 것이다.</br>
하지만 단점도 존재한다.</br>
Template Method Pattern 은 상속을 사용한다는 점이다. 상속은 부모와 자식이 강한 의존관계를 가지게 된다는 점이다.</br>
자식은 부모 클래스의 기능 전부 필요 하지 않을 수 있지만 상속을 하게 되면 모두 갖게 된다.

### **Strategy Pattern**

interface 를 사용하여 프로그램이 실행중에 변하는 알고리즘의 구현체를 선택할 수 있는 패턴이다. 이는 상속이 아니라 위임으로 문제를 해결하는 방법이다.

<img src="https://user-images.githubusercontent.com/74492426/209663837-ba57036e-7d21-4516-b5f5-b62e422f83f6.png" width="500px">

Strategy Pattern 은 Template Method Pattern 의 부모와 자식 관계의 강한 의존관계를 가지는 단점을 개선한 패턴이다.

### **Template Callback Pattern**

Template 는 변하지 않는 부분을 뜻하고 Callback 은 A 코드의 인수로 실행 가능한 B코드(Callback) 넘겨를 A코드를 실행하는 것을 말한다.</br>
자바 언어에서는 실행 가능한 코드를 인수로 넘기기 위해서는 객체가 필요한데, 객체는 전달해도 되고 java8 부터는 lamda 를 전달할 수 도 있다.

<img src="https://user-images.githubusercontent.com/74492426/209663875-162fde23-b9b2-47bc-aab3-d2d7199f9e14.png" width="500px">

> 이러한 패턴들은 실제로 스프링 안에서 많이 사용하는 패턴들이다.</br>
> XxxTemplate 객체가 이러한 패턴을 사용해서 만들었다.
>

### **Proxy Pattern**

프록시 패턴은 클라이언트가 A기능을 사용하고자 할때 B 라는 기능을 통해서 A 기능을 사용하는 것이다.</br>
굳이 B 기능을 통해서 A 기능을 사용해야 되냐는 의문을 가질 수 있지만 프록시 패턴의 핵심은 A 기능의 접근을 제어하는 것이다.

<img src="https://user-images.githubusercontent.com/74492426/209663905-128d2b7b-9366-456b-922d-ff60d8eb9df4.png" width="500px">

**프록시 사용 사례**

- 접근 제어
    - 권한에 따른 접근 제어
    - 캐싱
    - 지연 로딩
- 부가 기능 추가

### **Decorator Pattern**
Proxy Pattern 을 이용하여 부가기능을 추가하는 패턴이다.</br>
Proxy Pattern 과 Decorator Pattern 가 무슨 차이인지 의문점을 가질 수 있다.</br>
여기서 중요한점은 의도(intent) 이다.</br>
Proxy Pattern  의 의도는 접근 제어의 목적으로 사용하고, Decorator Pattern 은 새로운 기능을 추가하는 것이다.</br>
또, Decorator Pattern 은 프록시 패턴을 중복으로 사용하여 부가기능을 계속 추가할 수 있다.

1.
<img src="https://user-images.githubusercontent.com/74492426/209664011-832a420f-d839-4782-832f-cd4ff1d095f2.png" width="500px">
<img src="https://user-images.githubusercontent.com/74492426/209664018-e3cc0999-0cca-452f-81ee-29f2c1061495.png" width="500px">

2.
<img src="https://user-images.githubusercontent.com/74492426/209664024-7d6cdcaf-e93c-4291-b039-85db2b6cc13e.png" width="500px">

## 3. Aspect-Oriented  Programming(AOP)

해석하면 관점 지향 프로그래밍이라 하는데, 이는 어플리케이션을 바라는 관점을 하나하나의 기능에서 횡단 관심사(cross-cutting concerns) 관점으로 달리보는 것이다.

AOP 의 목적은 OOP를 대체하는 것이아니라 OOP를 지향하며 개발할 시에는 횡단 관심사 영역을 매끄럽게 처리할 수 없는데, 이러한 부분을 보조하는 것이다.
<img src="https://user-images.githubusercontent.com/74492426/209664039-d72c41dd-7ab0-437c-8e30-1e51c2bfdf57.png" width="500px">

### **AOP 적용 방식**

AOP 를 사용하면 핵심 기능과 부가 기능이 코드상 완전히 분리되어 관리할 수 있다.

부가 기능 로직은 실제 로직에 추가되는 방법은 총 3가지가 있다.

- 컴파일 시점
- 클래스 로딩 시점
- 런타임 시점(프록시)

### - **컴파일 시점**
<img src="https://user-images.githubusercontent.com/74492426/209664047-64e1942d-a5ca-42e8-a55b-686bdefe3e8f.png" width="500px">

.java 소스 코드를 컴파일러를 사용하여 .class 를 만드는 시점에 부가 기능 로직을 추가하는 방법이다.</br>
이 때는 AspectJ 가 제공하는 컴파일러를 사용해야 한다는 특징이 있다.</br>
컴파일된 .class 파일을 열어보면 부가 기능 코드가 추가된 것을 확인할 수 있다. (즉, 부가 기능 코드를 핵심 기능이 있는 코드에 추가한다고 보면 된다.)</br>
컴파일 시점에 부가 기능을 적용하려먼 특별한 컴파일러를 사용해야되고, 복잡하다는 단점이 있다.

### **- 클래스 로딩 시점**
<img src="https://user-images.githubusercontent.com/74492426/209664059-8bc5933d-095c-400d-a666-d59747347f0d.png" width="500px">

클래스 로딩 시점에 적용하는 것은 자바를 실행하면 자바 언어는 .class 파일을 JVM 내부의 클래스 로더에 보관하게 된다. 이 과정에서 .class 파일을 조작 후 JVM 에 올리는 방법이다.</br>
이렇게 클래스 로딩 시점에 .class 파일을 조작하는 건 모니터링 툴들이 많이 사용한다.</br>
이러한 방식의 단점은 자바를 실행할 때 특별한 옵션(java -javaagent) 을 통해 클래스 로더 조작기를 지정해야 하는데, 이부분이 번거롭고 운영하기 어렵다.

### - 런타임 시점
<img src="https://user-images.githubusercontent.com/74492426/209664069-1f196eea-08a1-4483-81f9-eb5433da4420.png" width="500px">

런타임 시점은 컴파일 과정이 끝나고, 클래스 로더에도 .class 파일이 모두 올라간 다음을 말한다.</br>
즉, 자바의 main 메서드가 이미 실행된 다음이다.</br>
런타임 시점의 AOP 는 프록시 방식을 사용한다.</br>
그러므로 '컴파일 시점',  '클래스 로딩 시점' 이 두가지 방식과 다르게 코드 외적으로 뭔가 관리할 필요가 없어진다.</br>
다만, 프록시를 사용하므로 AOP 기능의 일부 제약이 있다. (큰 문제는 없다.)

> 스프링 AOP 는 런타임 시점에 AOP 가 적용된다. (프록시 방식)</br>
> 프록시 방식의 AOP 는 메서드 실행 지점에만 AOP 를 적용할 수 있다. (오버라이딩 개념으로 동작하므로 생성자나 static 메서드, 필드 값 접근에는 프록시 개념을 적용할 수 없다.)</br>
> 그리고, 스프링 컨테이너가 관리할 수 있는 스프링 빈에만 AOP 를 적용할 수 있다.
> 
> 위와 같은 단점이 있는데도 불구하고 프록시 방식을 사용하는 이유가 무엇인지에 대해서 의문점을 가질 수 있다.</br>
> 하지만 이유는 간단하다.</br>
> 프록시 방식을 쓰면 제한되는 부분이 크게 제약되는 부분이 아니라는 점이다.</br>
> 실제 실무에서 프록시 방식의 단점으로 인해 개발하는데 문제가 되지 않고, 오히러 '컴파일 시점'과 '클래스 로더 시점' 에 사용해야될 컴파일러나 java  agent 를 관리하는 것이 더 까다롭다.


### AOP 용어 정리 (Spring  AOP 포함)
<img src="https://user-images.githubusercontent.com/74492426/209664081-dd45ef5b-940f-43e6-96dc-935beb7ae9e1.png" width="500px">

- Join Point
    - 추상적인 개념이며 AOP 를 적용할 수 있는 모든 지점
    - Spring AOP 는 Proxy 방식을 사용하므로 항상 메소드 실행 지점으로 제한된다.
- Pointcut
    - Join Point 중에서 Advice 가 적용될 위치를 선별하는 기능
    - 주로 AspectJ 표현식을 사용해서 지정
    - Spring AOP 는 메서드 실행 지점만 Pointcut 으로 선별할 수 있다.
- Target
    - Advice 를 받는 객체
- Advice
    - 부가 기능
    - Around (주변), Before (전), After (후) 와 같은 다양한 종류의 Advice가 있다.
- Aspect
    - Advice + Pointcut 을 모듈화한것이다. (@Aspect를 생각하면 된다.)
- Advisor
    - 하나의 Advisor 와 하나의 Pointcut 으로 구성되어 있다. (Spring AOP 에서만 사용되는 특별한 용어)
- Weaving
    - Pointcut 으로 결정한 Target 의 Join Point 에 Advice 를 적용하는 것
    - AOP 적용을 위해 Aspect 객체에 연결한 상태 - 3가지 방법
        - 컴파일 타임
        - 클래스 로드 타임
        - 런타임
    - Spring AOP 는 런타임 방식의  프록시 패턴을 사용
- AOP Proxy
    - AOP 기능을 구현하기 위해 만든 프록시 객체이다
    - Spring 에서는 AOP 프록시를 JDK 동적 프록시 or CGLIB 프록시를 사용한다. (default 는 CGLIB 이다)
