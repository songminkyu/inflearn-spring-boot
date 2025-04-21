# 스프링 입문 - 코드로 배우는 스프링 부트, 웹 MVC, DB 접근 기술
## 다시 시작.
> Spring으로 처음 입문해서 한 5년정도 사용하다가 스타트업으로 이직하면서 Spring 대신 NestJS로 진로를 바꿨다. 물론  NodeJS나 NestJS가 Spring보다는 개발 속도도 빠르고 비동기에 특화되어 있어 만족은 하지만... 마치 그  NodeJS나 NestJS가 Spring을 다 채우지 못하는 그 공허험이 존재 한다. 아마 나같이 Spring에서 NestJS, NodeJS로 전향한 사람은 다 이해 할 것다.ㅠ
최근 NestJS를 시작하면서 NestJS가 Spring과 비슷한 철학을 가진 프레임워크여서 스프링을 다시 학습을 할 필요 성이 느껴졌다.<br>

- 본 블로그 게시글에서는 인프런의 스프링 입문(코드로 배우는 스프링 부터, 웹 MVC, DB 접근기술)강의의 내용정리 보다는 필자가 학습하고 필자가 부족한 내용을 학습해서 공부한 내용을 정리 할 것입니다.
- 인프런 강의에서 당장하는 Thymeleaf는 다루지 않고 대신에 Swagger도입하여 HTTP API를 통하여 작동하는것으로 변경하였니다.

## 개발환경
- windows 10
- JAVA 17
- IntelliJ

## Dispatcher-Servlet
### 디스패처 서블릿이란?
- 디스패쳐 서블릿은 **가장 앞단에서 HTTP 프로토콜로 들어오는 모든 요청을 가장 먼저 받아 적합한 컨트롤러에 위임해주는 프론트 컨트롤러라고 정의 할 수 있다.** <br> 여기서의 **프론트 컨트롤러란 주로 서블릿 컨테이너의 제일 앞에서 서버로 들어오는 클라이언트의 모든 요청을 받아서 처리해주는 컨트롤러를 말한다.**
- 즉 클라이언트로부터 어떠한 요청이 오면 Tomcat과 같은 서블릿컨테이너가 요청을 받는 이때 제일 앞에서 서버로 들어오는 모든 요청을 처리하는 프론터 컨트롤러를 디스패처 서블릿이라고 한다.

![](https://images.velog.io/images/hong-brother/post/dbcffef6-4c80-42a0-8320-c84f3dca102a/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202022-03-01%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%208.53.30.png)

## Spring DI(Dependency Injection)
### DI(Dependency Injection) 이란?
- 스프링이 다른 프레임워크와 차별화되어 제공하는 의존 관계 주입 기능으로, 객체를 직접 생성하는게 아니라 외부에서 생성한 후 주입 시키는 방법이다.

### Field Injection
```java
@Controller
public class HomeController {
    @Autowired
    private HomeService homeService;
}
```
가장 간단한 방식의 선언 방법 이지만. **의존 관계가 눈에 잘 보이지 않아 추상적이고, 이로 인해 의존성 관계가 과도하게 복잡해질 수 있다.**(@Autowired 선언 아래 3개든 10개든 ... 많이 추가 할 수록 의존 관계가 눈에 눈에 잘 보이지 않음)
또한 단일 책입 원칙에 반하는 안티패턴이다.


### Setter Injection
```java
@Controller
public class HomeController {
    private HomeService homeService;
    
    @Autowired
    public void setHomeService(HomeService homeService) {
    	this.homeService = homeService;
    }
}
```
Setter Injection을 통해서 homeService를 주입하지 않아도 HomeController 객체가 생성이 가능하며 주입하지 않고 호출시 NullPointException이 발생한다.
**즉 주입이 필요한 객체가 주입을 받지 않고 객체 생성이 된다는 문제가 발생한다.**

### Constructor Injection
- 일반적인 생성자 주입
```java
@Controller
public class HomeController {
    private final HomeService homeService;
    
    public HomeController(HomeService homeService) {
    	this.homeService = homeService;
    }
}
```
- lombok를 이용한 생성자 주입
```java
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;
}
```
Spring Framework Reference에서 권장하는 방법이다.
생성자 주입 방식은 필수적으로 사용해야하는 의존성 없이는 Instance를 만들지 못하도록 강제 할 수 있기 때문이다.

## Test 작성
- 테스트 케이스 문법 (given - when - then)
```java
    @Test
    void 회원가입() {
        // 테스트 케이스 문법
        // given : 주어진 상황
        Member member = new Member();
        member.setName("hello");

        // when : 실행 했을때
        Long saveId = memberService.join(member);

        // then : 예상 되는 결과
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }
```
- 테스트 코드는 프로덕션코드로 나가는 것이 아니기 때문에, 영어권 사람들과 일하는 것이 아니기 때문에 **직관적**으로 한글로 적어도 무관하다.

## IntelliJ Gradle 대신에 자바 직접 실행
- 최근 IntelliJ 버전은 Gradle을 통해서 실행 하는 것이 기본 설정이다. 이렇게 하면 실행속도가 느리다.
  다음과 같이 변경하면 자바로 바로 실행해서 실행속도가 더 빠르다.

- Preferences
    - Build, Execution, Deployment
        - Build Tools
            - Gradle
                - Build and run using: Gradle IntelliJ IDEA
                - Run tests using: Gradle IntelliJ IDEA

![](https://images.velog.io/images/hong-brother/post/ee8fa673-7e3a-4bf6-992b-21dc816c28ec/buildTool.png)

## 단축기(macOS)
- Cmd + Shift + Enter : 문자 자동 완성
- Shift + F6 : 변수 이름 변경
- Cmd + Option + v : 변수 추출하기
- Cmd + Shift + T : 해당 클래스에 대한 테스트 케이스 생성
- control + enter : Code/Generate

## 참고문헌
[스프링 입문 - 코드로 배우는 스프링 부트, 웹 MVC, DB 접근 기술](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%EC%9E%85%EB%AC%B8-%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8)

## Github
[hello-spring](https://github.com/hong-brother/hello-spring)