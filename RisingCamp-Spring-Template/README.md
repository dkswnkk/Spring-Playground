# Spring Boot Template
본 템플릿은 소프트스퀘어드 서버 교육용 Spring Boot 템플릿 입니다. (2021 ver.)

## ✨Common
### REST API
REST API의 기본 구성 원리를 반드시 구글링하여 익힌 뒤에 Route를 구성하자.

### Folder Structure
- `src`: 메인 로직
  `src`에는 도메인 별로 패키지를 구성하도록 했다. **도메인**이란 회원(User), 게시글(Post), 댓글(Comment), 주문(Order) 등 소프트웨어에 대한 요구사항 혹은 문제 영역이라고 생각하면 된다. 각자 설계할 APP을 분석하고 필요한 도메인을 도출하여 `src` 폴더를 구성하자.
- `config` 및 `util` 폴더: 메인 로직은 아니지만 `src` 에서 필요한 부차적인 파일들을 모아놓은 폴더
- 도메인 폴더 구조
> Route - Controller - Provider/Service - DAO

- Route: Request에서 보낸 라우팅 처리
- Controller: Request를 처리하고 Response 해주는 곳. (Provider/Service에 넘겨주고 다시 받아온 결과값을 형식화), 형식적 Validation
- Provider/Service: 비즈니스 로직 처리, 의미적 Validation
- DAO: Data Access Object의 줄임말. Query가 작성되어 있는 곳.

- 메소드 네이밍룰
  이 템플릿에서는 사용되는 메소드 명명 규칙은 User 도메인을 참고하자. 항상 이 규칙을 따라야 하는 것은 아니지만, 네이밍은 통일성 있게 해주는 게 좋다.


### Comparison
3개 템플릿 모두 다음과 같이 Request에 대해 DB 단까지 거친 뒤, 다시 Controller로 돌아와 Response 해주는 구조를 갖는다. 구조를 먼저 이해하고 템플릿을 사용하자.
> `Request` -> Route -> Controller -> Service/Provider -> DAO -> DB

> DB -> DAO -> Service/Provider -> Controller -> Route -> `Response`

다음은 각 템플릿 별 차이점을 비교 기술해 놓은 것이다.
#### PHP (패키지매니저 = composer)
> Request(시작) / Response(끝) ⇄ Router (index.php) ⇄ Controller  ⇄ Service (CUD) / Provider (R) ⇄ PDO (DB)

#### Node.js (패키지매니저 = npm)
> Request(시작) / Response(끝)  ⇄ Router (*Route.js) ⇄ Controller (*Controller.js) ⇄ Service (CUD) / Provider (R) ⇄ DAO (DB)

#### Springboot java (패키지매니저 = Maven (= Spring 선호), Gradle (Springboot 선호))
> Request(시작) / Response(끝) ⇄ Controller(= Router + Controller) ⇄ Service (CUD) / Provider (R) ⇄ DAO (DB)

### Validation
서버 API 구성의 기본은 Validation을 잘 처리하는 것이다. 외부에서 어떤 값을 날리든 Validation을 잘 처리하여 서버가 터지는 일이 없도록 유의하자.
값, 형식, 길이 등의 형식적 Validation은 Controller에서,
DB에서 검증해야 하는 의미적 Validation은 Provider 혹은 Service에서 처리하면 된다.

## ✨Structure
앞에 (*)이 붙어있는 파일(or 폴더)은 추가적인 과정 이후에 생성된다.
```text
api-server-spring-boot
  > * build
  > gradle
  > * logs
    | app.log // warn, error 레벨에 해당하는 로그가 작성 되는 파일
    | app-%d{yyyy-MM-dd}.%i.gz
    | error.log // error 레벨에 해당하는 로그가 작성 되는 파일
    | error-%d{yyyy-MM-dd}.%i.gz
  > src.main.java.com.example.demo
    > config
      > secret
        | Secret.java // git에 추적되지 않아야 할 시크릿 키 값들이 작성되어야 하는 곳
      | BaseException.java // Controller, Service, Provider 에서 Response 용으로 공통적으로 사용 될 익셉션 클래스
      | BaseResponse.java // Controller 에서 Response 용으로 공통적으로 사용되는 구조를 위한 모델 클래스
      | BaseResponseStatus.java // Controller, Service, Provider 에서 사용 할 Response Status 관리 클래스 
      | Constant.java // 공통적으로 사용될 상수 값들을 관리하는 곳
    > src
      > test
        | TestController.java // logger를 어떻게 써야하는지 보여주는 테스트 클래스
      > user
        > models
          | GetUserRes.java        
          | PostUserReq.java 
          | PostUserRes.java 
        | UserController.java
        | UserProvider.java
        | UserService.java
        | UserDao.java
      | WebSecurityConfig.java // spring-boot-starter-security, jwt 를 사용하기 위한 클래스 
    > utils
      | AES128.java // 암호화 관련 클래스
      | JwtService.java // jwt 관련 클래스
      | ValidateRegex.java // 정규표현식 관련 클래
    | DemoApplication // SpringBootApplication 서버 시작 지점
  > resources
    | application.yml // Database 연동을 위한 설정 값 세팅 및 Port 정의 파일
    | logback-spring.xml // logger 사용시 console, file 설정 값 정의 파일
build.gradle // gradle 빌드시에 필요한 dependency 설정하는 곳
.gitignore // git 에 포함되지 않아야 하는 폴더, 파일들을 작성 해놓는 곳

```
## ✨Description

### Annotation
스프링 부트는 `어노테이션`을 다양하게 아는 것이 중요하다. SpringBoot의 시작점을 알리는 `@SpringBootApplication` 어노테이션 뿐만 아니라 `스프링 부트 어노테이션` 등의 키워드로 구글링 해서 **스프링 부트에서 자주 사용되는 다양한 어노테이션을 이해하고 외워두자.**

### Lombok
Java 라이브러리로 반복되는 getter, setter, toString 등의 메서드 작성 코드를 줄여주는 라이브러리이다. 기본적으로 각 도메인의 model 폴더 내에 생성하는 클래스에 lombok을 사용하여 코드를 효율적으로 짤 수 있도록 구성했다. 자세한 내용은 구글링과 model > PostUser, User를 통해 이해하자.


### src - main - resources
템플릿은 크게 log 폴더와 src 폴더로 나뉜다. log는 통신 시에 발생하는 오류들을 기록하는 곳이다. 실제 메인 코드는 src에 담겨있다. src > main > resources를 먼저 살펴보자.

`application.yml`

에서 **포트 번호를 정의**하고 **DataBase 연동**을 위한 값을 설정한다.

`logback-spring.xml`

logs 폴더에 로그 기록을 어떤 형식으로 남길 것인지 설정한다. logs 폴더에 어떻게 기록이 남겨져 있는지 확인해보시라. (커스텀 하지 않아도 된다면`logback-spring.xml` 를 수정할 필요는 없다.)

### src - main - java

`com.example.demo` 패키지에는 크게 `config` 폴더, `src` 폴더와 이 프로젝트의 시작점인 `DemoApplication.java`가 있다.

`DemoApplication.java` 은 스프링 부트 프로젝트의 시작을 알리는 `@SpringBootApplication` 어노테이션을 사용하고 있다. (구글링 통해 `@SpringBootApplication`의 다른 기능도 살펴보자.)

`src`폴더에는 실제 **API가 동작하는 프로세스**를 담았고 `config` 폴더에는 `src`에서 필요한 Secret key, Base 클래스, 상수 클래스를, `util` 폴더에는 JWT, 암호화, 정규표현식 등의 클래스를 모아놨다.

`src`를 자세하게 살펴보자. `src`는 각 **도메인**별로 패키지를 구분해 놓는다. 현재는 `user` 도메인과 `test` 도메인이 있다. **도메인**이란 게시글, 댓글, 회원, 정산, 결제 등 소프트웨어에 대한 요구사항 혹은 문제 영역이라고 생각하면 된다.

이 도메인들은 API 통신에서 어떤 프로세스로 처리되는가? API 통신의 기본은 Request → Response이다. 스프링 부트에서 **어떻게 Request를 받아서, 어떻게 처리하고, 어떻게 Response 하는지**를 중점적으로 살펴보자. 전반적인 API 통신 프로세스는 다음과 같다.

> **Request** → `XXXController.java`(=Router+Controller) → `Service` (CUD) / `Provider` (R) (=Business Logic) → `Dao` (DB) → **Response**

#### 1. Controller / `UserController.java`  / @RestController

> 1) API 통신의 **Routing** 처리
> 2) Request를 다른 계층에 넘기고 처리된 결과 값을 Response 해주는 로직
>  + Request의 **형식적 Validation** 처리 (DB를 거치지 않고도 검사할 수 있는)

**1) `@Autowired`**

UserController의 생성자에 `@Autowired` 어노테이션이 붙어있다. 이는 **의존성 주입**을 위한 것으로, `UserController`  뿐만 아니라 다음에 살펴볼 `UserService`, `UserProvider`의 생성자에도 각각 붙어 있는 것을 확인할 수 있다. 간단히 요약하면 객체 생성을 자동으로 해주는 역할이다. 자세한 프로세스는 구글링을 통해 살펴보자.

나머지 어노테이션들 역시 구글링을 통해 이해하자.

**2) `BaseResponse`**

Response할 때, 공통 부분은 묶고 다른 부분은 제네릭을 통해 구현함으로써 반복되는 코드를 줄여준다. (`BaseResponse.java` 코드 살펴 볼 것. 여기에 쓰이는`BaseResponseStatus` 는 `enum`을 통해 Status 값을 관리하고 있다.)

**3) 메소드 네이밍룰**

이 템플릿에서는 사용되는 메소드 명명 규칙은 다음과 같다.

> HTTP Method + 핵심 URI

- **GET** `/users` 를 처리하는 메소드명 → getUsers
- **PATCH** `/users` 를 처리하는 메소드명 →patchUsers

항상 이 규칙을 따라야 하는 것은 아니지만, 네이밍은 통일성 있게 해주는 게 좋다.

**4) Res, Req 네이밍룰**

각 메소드에서 사용되는 Res, Req 모델의 명명 규칙도 메소드 명과 비슷하다.

> HTTP Method + 핵심 URI +**Res/Req**

**Patch** `/users/:userId` → PatchUserRes / PatchUserReq

이 Res, Req 모델은 `(도메인명) / models` 폴더에 만들면 된다.

#### 2. Service 와 Provider / `UserService.java` `UserProvider.java` / @Service

> 1) **비즈니스 로직**을 다루는 곳 (DB 접근[CRUD], DB에서 받아온 것 형식화)
>  + Request의 **의미적** **Validation** 처리 (DB를 거쳐야 검사할 수 있는)

`Service`와 `Provider`는 비즈니스 로직을 다루는 곳이다. **CRUD** 중 **R(Read)** 에 해당하는 코드가 긴 경우가 많기 때문에 **R(Read)** 만 따로 분리해 `Service`는 **CUD(Create, Update, Delete)** 를, `Provider`는 **R(Read)** 를 다루도록 했다. 유지 보수가 용이해진다.

`Provider`
> **R(Read)** 와 관련된 곳이다. DB에서 select 해서 얻어온 값을 가공해서 뱉어준다.

`Service`
> **CUD(Create, Update, Delete)** 와 관련된 곳이다. **CUD**에서 **R**이 필요한 경우가 있는데, 그럴 때는 `Provider`에 구성되어 있는 것을 `Service`에서 사용하면 된다.

**1) 메소드명**

메소드의 prefix로 다음 규칙을 따르고 있다.

C → createXXX `createInfo`

R → retrieveXXX `retrieveInfoList`

U → updateXXX `updateInfo`

D → deleteXXX `deleteInfo`

**2) BaseException**

`BaseException`을 통해 `Service`나 `Provider`에서 `Controller`에 Exception을 던진다. 마찬가지로 Status 값은 `BaseResponseStatus` 의 `enum`을 통해 관리한다.

#### 3. DAO / `UserDao.java`
JdbcTemplate을 사용하여 구성되어 있다. 자세한 내용은 이곳 [공식 문서](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html) 와 템플릿의 기본 예제를 참고하자.

## ✨Usage
### API 만들기 예제
로컬에서 DemoApplication을 실행시킨다. (로컬 서버 구동 시)

[DB 연결 없이 TEST]
1. src > test > TestController.java에 구성되어 있는 API를 테스트해보자.
2. 포스트맨을 통해 GET localhost:9000/test/log로 테스트가 잘 되는지 확인한다.

[DB 연결 이후 TEST]
1. resources > application.yml에서 본인의 DB 정보를 입력한다.
2. DB에 TEST를 위한 간단한 테이블을 하나 만든다.
3. UserController.java, UserProvider.java, UserService.java, UserDao.java를 구성하여 해당 테이블의 값들을 불러오는 로직을 만든다.
4. 포스트맨을 통해 본인이 만든 API 테스트가 잘 되는지 확인한다.

### nohup
무중단 서비스를 위해 nohup을 사용한다. 자세한 내용은 환경 구축 실습 영상을 참고하자.

### Error
서버 Error를 마주했다면, 원인을 파악할 수 있는 다양한 방법들을 통해 문제 원인을 찾자.
- 컴파일 에러 확인
- log 폴더 확인
- 그 외 방법들

## ✨License
- 본 템플릿의 소유권은 소프트스퀘어드에 있습니다. 본 자료에 대한 상업적 이용 및 무단 복제, 배포 및 변경을 원칙적으로 금지하며 이를 위반할 때에는 형사처벌을 받을 수 있습니다.