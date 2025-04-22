# 객체지향 스프링부트 (Object-Oriented Spring Boot)

## 목차
- [스프링 프레임워크 소개](#스프링-프레임워크-소개)
- [스프링의 역사](#스프링의-역사)
- [스프링의 핵심 기능](#스프링의-핵심-기능)
- [스프링 부트란?](#스프링-부트란)
- [객체지향 프로그래밍](#객체지향-프로그래밍)
- [다형성(Polymorphism)](#다형성polymorphism)
- [객체지향 설계의 5가지 원칙 (SOLID)](#객체지향-설계의-5가지-원칙-solid)
  - [단일 책임 원칙 (SRP)](#단일-책임-원칙-srp)
  - [개방-폐쇄 원칙 (OCP)](#개방-폐쇄-원칙-ocp)
  - [리스코프 치환 원칙 (LSP)](#리스코프-치환-원칙-lsp)
  - [인터페이스 분리 원칙 (ISP)](#인터페이스-분리-원칙-isp)
  - [의존관계 역전 원칙 (DIP)](#의존관계-역전-원칙-dip)
- [스프링과 객체지향](#스프링과-객체지향)
- [의존성 주입 (DI)](#의존성-주입-di)

## 스프링 프레임워크 소개

스프링 프레임워크는 2002년 EJB(Enterprise JavaBeans)의 복잡성과 높은 개발 비용(약 30,000달러)에 대한 대안으로 등장했습니다. 스프링은 BeanFactory, ApplicationContext, POJO(Plain Old Java Object) 기반의 경량 프레임워크로, 개발 생산성과 코드 품질을 향상시키는 솔루션을 제공합니다.

주요 창시자:
- Juergen Hoeller(독일)
- Yann Caroff(프랑스)

스프링은 기존 J2EE(EJB) 시스템의 복잡성을 해결하고자 탄생했습니다.

## 스프링의 역사

- **2003년**: 스프링 1.0 출시 - XML 기반 설정
- **2006년**: 스프링 2.0 출시 - XML 기반 설정 개선
- **2009년**: 스프링 3.0 출시 - 애너테이션 기반 설정 도입
- **2013년**: 스프링 4.0 출시 - Java 8 지원
- **2014년**: 스프링 부트 1.0 출시
- **2017년**: 스프링 5.0, 스프링 부트 2.0 출시 - 리액티브 프로그래밍 지원
- **2020년 9월**: 스프링 5.2.x, 스프링 부트 2.3.x 출시

## 스프링의 핵심 기능

스프링 프레임워크는 모듈화된 구조로 다양한 기능을 제공합니다:

- **핵심 기술**: DI 컨테이너, AOP, 이벤트, 리소스 등
- **웹 기술**: MVC, WebFlux 등
- **데이터 접근**: 트랜잭션, JDBC, ORM 지원, XML 처리
- **통합**: 원격 지원, JMS, 이메일, 스케줄링 등
- **테스트**: 스프링 테스트 프레임워크
- **언어**: 코틀린, 그루비 등 지원
- **기타**: Rest Docs 등

![스프링 모듈 구조](https://spring.io/img/spring-by-pivotal.png)

## 스프링 부트란?

스프링 부트는 스프링의 복잡한 설정을 간소화하고 빠르게 개발할 수 있게 해주는 프레임워크입니다:

- **자동 구성**: 복잡한 설정을 자동화
- **내장 서버**: Tomcat 등 내장
- **스타터 의존성**: 필요한 라이브러리를 쉽게 추가
- **3rd party 라이브러리 자동 구성**: 외부 라이브러리 통합 간소화
- **메트릭, 상태 확인, 외부 설정 등**: 운영 기능 제공

## 객체지향 프로그래밍

객체지향 프로그래밍은 이론이 아닌 실전에서 중요합니다. 객체지향의 핵심은 다음과 같은 질문에 답하는 것입니다:

- 왜 필요한가?
- 객체지향이란 무엇인가?
- 객체지향의 본질은 무엇인가?
- 객체지향을 실무에서 어떻게 적용할 수 있는가?

객체지향이 필요한 이유:
- 역할과 구현을 분리하여 유연하고 변경이 용이한 프로그램을 만들기 위함
- 다양한 요구사항 변경에 대응하기 위함
- 클라이언트 코드의 변경 없이 서버 측 구현을 바꿀 수 있게 함

## 다형성(Polymorphism)

다형성은 객체지향의 핵심 원리입니다:

- 역할과 구현을 분리하여 1:1 관계가 아닌 1:N 관계를 가능하게 함
- 클라이언트는 대상의 역할(인터페이스)만 알면 됨
- 클라이언트는 구현 대상의 내부 구조를 알 필요가 없음

```java
// 다형성 예시: 자동차 인터페이스와 구현체

// 인터페이스(역할)
public interface Car {
    void drive();
    void stop();
}

// 구현체 1
public class K3Car implements Car {
    @Override
    public void drive() {
        System.out.println("K3 자동차가 주행합니다.");
    }
    
    @Override
    public void stop() {
        System.out.println("K3 자동차가 정지합니다.");
    }
}

// 구현체 2
public class TeslaCar implements Car {
    @Override
    public void drive() {
        System.out.println("테슬라 자동차가 전기로 주행합니다.");
    }
    
    @Override
    public void stop() {
        System.out.println("테슬라 자동차가 정지합니다.");
    }
}

// 클라이언트(운전자)
public class Driver {
    private Car car;
    
    public Driver(Car car) { // 어떤 자동차든 주입받을 수 있음
        this.car = car;
    }
    
    public void drive() {
        car.drive();
    }
    
    public void stop() {
        car.stop();
    }
}

// 사용 예시
public class Main {
    public static void main(String[] args) {
        // K3 자동차로 운전
        Car k3 = new K3Car();
        Driver driver1 = new Driver(k3);
        driver1.drive(); // "K3 자동차가 주행합니다."
        
        // 테슬라 자동차로 변경해도 운전자 코드는 변경되지 않음
        Car tesla = new TeslaCar();
        Driver driver2 = new Driver(tesla);
        driver2.drive(); // "테슬라 자동차가 전기로 주행합니다."
    }
}
```

다형성의 본질:
- 인터페이스를 구현한 객체 인스턴스를 실행 시점에 유연하게 변경할 수 있음
- 클라이언트를 변경하지 않고 서버의 구현 기능을 유연하게 변경할 수 있음

## 객체지향 설계의 5가지 원칙 (SOLID)

SOLID는 객체지향 설계의 5가지 기본 원칙을 의미합니다:

### 단일 책임 원칙 (SRP)
- Single Responsibility Principle
- 한 클래스는 하나의 책임만 가져야 함
- 변경이 있을 때 파급 효과가 적어야 함

**SRP 위반 예시:**
```java
// 하나의 클래스가 여러 책임을 가지는 경우
public class UserService {
    public void addUser(String username, String email) {
        // 사용자 정보 유효성 검사
        if (username == null || email == null) {
            throw new IllegalArgumentException("유효하지 않은 사용자 정보입니다.");
        }
        
        // 데이터베이스에 사용자 정보 저장
        String sql = "INSERT INTO users (username, email) VALUES (?, ?)";
        // DB 연결 및 쿼리 실행 코드...
        
        // 사용자 등록 이메일 발송
        sendEmail(email, "회원 가입을 환영합니다!", "...");
    }
    
    private void sendEmail(String to, String subject, String body) {
        // 이메일 발송 로직...
    }
}
```

**SRP 준수 예시:**
```java
// 각 책임을 별도의 클래스로 분리
public class UserValidator {
    public void validate(String username, String email) {
        if (username == null || email == null) {
            throw new IllegalArgumentException("유효하지 않은 사용자 정보입니다.");
        }
    }
}

public class UserRepository {
    public void save(String username, String email) {
        String sql = "INSERT INTO users (username, email) VALUES (?, ?)";
        // DB 연결 및 쿼리 실행 코드...
    }
}

public class EmailService {
    public void sendWelcomeEmail(String email) {
        sendEmail(email, "회원 가입을 환영합니다!", "...");
    }
    
    private void sendEmail(String to, String subject, String body) {
        // 이메일 발송 로직...
    }
}

public class UserService {
    private final UserValidator validator;
    private final UserRepository repository;
    private final EmailService emailService;
    
    public UserService(UserValidator validator, UserRepository repository, EmailService emailService) {
        this.validator = validator;
        this.repository = repository;
        this.emailService = emailService;
    }
    
    public void addUser(String username, String email) {
        validator.validate(username, email);
        repository.save(username, email);
        emailService.sendWelcomeEmail(email);
    }
}
```

### 개방-폐쇄 원칙 (OCP)
- Open/Closed Principle
- 소프트웨어 요소는 확장에는 열려 있으나 변경에는 닫혀 있어야 함
- 기존 코드를 변경하지 않고 기능을 확장할 수 있어야 함

**OCP 위반 예시:**
```java
// 결제 처리 로직이 결제 유형에 따라 분기되는 경우
public class PaymentProcessor {
    public void processPayment(String paymentType, double amount) {
        if ("CREDIT_CARD".equals(paymentType)) {
            processCreditCardPayment(amount);
        } else if ("PAYPAL".equals(paymentType)) {
            processPayPalPayment(amount);
        } else if ("BITCOIN".equals(paymentType)) {
            processBitcoinPayment(amount);
        }
        // 새로운 결제 방식이 추가될 때마다 이 메서드를 수정해야 함
    }
    
    private void processCreditCardPayment(double amount) {
        // 신용카드 결제 로직...
    }
    
    private void processPayPalPayment(double amount) {
        // 페이팔 결제 로직...
    }
    
    private void processBitcoinPayment(double amount) {
        // 비트코인 결제 로직...
    }
}
```

**OCP 준수 예시:**
```java
// 결제 방식을 인터페이스로 추상화
public interface PaymentMethod {
    void processPayment(double amount);
}

public class CreditCardPayment implements PaymentMethod {
    @Override
    public void processPayment(double amount) {
        // 신용카드 결제 로직...
    }
}

public class PayPalPayment implements PaymentMethod {
    @Override
    public void processPayment(double amount) {
        // 페이팔 결제 로직...
    }
}

public class BitcoinPayment implements PaymentMethod {
    @Override
    public void processPayment(double amount) {
        // 비트코인 결제 로직...
    }
}

// 결제 처리기는 구체적인 결제 방식에 의존하지 않음
public class PaymentProcessor {
    private PaymentMethod paymentMethod;
    
    public PaymentProcessor(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public void processPayment(double amount) {
        paymentMethod.processPayment(amount);
    }
}

// 새로운 결제 방식 추가
public class KakaoPayPayment implements PaymentMethod {
    @Override
    public void processPayment(double amount) {
        // 카카오페이 결제 로직...
    }
}

// 사용 예시
public class Main {
    public static void main(String[] args) {
        // 신용카드 결제
        PaymentProcessor creditCardProcessor = new PaymentProcessor(new CreditCardPayment());
        creditCardProcessor.processPayment(100.0);
        
        // 카카오페이 결제 (PaymentProcessor 코드 수정 없이 새로운 결제 방식 추가)
        PaymentProcessor kakaoPayProcessor = new PaymentProcessor(new KakaoPayPayment());
        kakaoPayProcessor.processPayment(100.0);
    }
}
```

### 리스코프 치환 원칙 (LSP)
- Liskov Substitution Principle
- 프로그램의 객체는 프로그램의 정확성을 깨뜨리지 않으면서 하위 타입의 인스턴스로 바꿀 수 있어야 함

**LSP 위반 예시:**
```java
// 직사각형 클래스
public class Rectangle {
    protected int width;
    protected int height;
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public int getArea() {
        return width * height;
    }
}

// 정사각형은 직사각형의 특별한 경우로 상속
public class Square extends Rectangle {
    @Override
    public void setWidth(int width) {
        this.width = width;
        this.height = width;  // 정사각형은 가로=세로 유지
    }
    
    @Override
    public void setHeight(int height) {
        this.height = height;
        this.width = height;  // 정사각형은 가로=세로 유지
    }
}

// 테스트 코드
public class Main {
    public static void main(String[] args) {
        testRectangle(new Rectangle());  // 통과
        testRectangle(new Square());     // 실패! LSP 위반
    }
    
    public static void testRectangle(Rectangle rectangle) {
        rectangle.setWidth(4);
        rectangle.setHeight(5);
        
        // 직사각형이라면 넓이는 4*5=20이어야 함
        if (rectangle.getArea() != 20) {
            System.out.println("테스트 실패: 면적이 20이어야 하지만 " + rectangle.getArea() + "입니다.");
        }
    }
}
```

**LSP 준수 예시:**
```java
// 도형 인터페이스 정의
public interface Shape {
    int getArea();
}

// 직사각형 구현
public class Rectangle implements Shape {
    private int width;
    private int height;
    
    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    @Override
    public int getArea() {
        return width * height;
    }
}

// 정사각형 구현 (직사각형 상속 대신 별도 구현)
public class Square implements Shape {
    private int side;
    
    public Square(int side) {
        this.side = side;
    }
    
    public void setSide(int side) {
        this.side = side;
    }
    
    public int getSide() {
        return side;
    }
    
    @Override
    public int getArea() {
        return side * side;
    }
}

// 테스트 코드
public class Main {
    public static void main(String[] args) {
        Rectangle rectangle = new Rectangle(4, 5);
        System.out.println("직사각형 면적: " + rectangle.getArea());  // 20
        
        Square square = new Square(5);
        System.out.println("정사각형 면적: " + square.getArea());     // 25
        
        // Shape 인터페이스를 사용하는 메서드에 어떤 구현체든 전달 가능
        printArea(rectangle);
        printArea(square);
    }
    
    public static void printArea(Shape shape) {
        System.out.println("도형의 면적: " + shape.getArea());
    }
}
```

### 인터페이스 분리 원칙 (ISP)
- Interface Segregation Principle
- 특정 클라이언트를 위한 인터페이스 여러 개가 범용 인터페이스 하나보다 낫다

**ISP 위반 예시:**
```java
// 만능 기기 인터페이스
public interface MultiFunctionDevice {
    void print();
    void scan();
    void fax();
    void copy();
}

// 모든 기능이 필요한 구현체
public class OfficeMultiFunctionPrinter implements MultiFunctionDevice {
    @Override
    public void print() {
        // 인쇄 기능 구현
    }
    
    @Override
    public void scan() {
        // 스캔 기능 구현
    }
    
    @Override
    public void fax() {
        // 팩스 기능 구현
    }
    
    @Override
    public void copy() {
        // 복사 기능 구현
    }
}

// 일부 기능만 필요한 구현체 (불필요한 메서드도 구현해야 함)
public class HomePrinter implements MultiFunctionDevice {
    @Override
    public void print() {
        // 인쇄 기능 구현
    }
    
    @Override
    public void scan() {
        // 스캔 기능 구현
    }
    
    @Override
    public void fax() {
        throw new UnsupportedOperationException("가정용 프린터는 팩스 기능을 지원하지 않습니다.");
    }
    
    @Override
    public void copy() {
        // 복사 기능 구현
    }
}
```

**ISP 준수 예시:**
```java
// 기능별로 분리된 인터페이스
public interface Printer {
    void print();
}

public interface Scanner {
    void scan();
}

public interface Fax {
    void fax();
}

public interface Copier {
    void copy();
}

// 필요한 인터페이스만 구현
public class OfficeMultiFunctionPrinter implements Printer, Scanner, Fax, Copier {
    @Override
    public void print() {
        // 인쇄 기능 구현
    }
    
    @Override
    public void scan() {
        // 스캔 기능 구현
    }
    
    @Override
    public void fax() {
        // 팩스 기능 구현
    }
    
    @Override
    public void copy() {
        // 복사 기능 구현
    }
}

// 필요한 기능만 구현
public class HomePrinter implements Printer, Scanner, Copier {
    @Override
    public void print() {
        // 인쇄 기능 구현
    }
    
    @Override
    public void scan() {
        // 스캔 기능 구현
    }
    
    @Override
    public void copy() {
        // 복사 기능 구현
    }
}

// 클라이언트 코드
public class User {
    public void printDocument(Printer printer) {
        printer.print();
    }
    
    public void scanDocument(Scanner scanner) {
        scanner.scan();
    }
}
```

### 의존관계 역전 원칙 (DIP)
- Dependency Inversion Principle
- "추상화에 의존해야지, 구체화에 의존하면 안된다."
- 구현 클래스에 의존하지 말고, 인터페이스에 의존해야 함

**DIP 위반 예시:**
```java
// 구체적인 저장소 구현에 직접 의존
public class MemberService {
    private MemoryMemberRepository repository = new MemoryMemberRepository();
    
    public void signUp(String name, String email) {
        repository.save(new Member(name, email));
    }
    
    // 저장소를 변경하려면 코드를 수정해야 함
    // private JdbcMemberRepository repository = new JdbcMemberRepository();
}

public class MemoryMemberRepository {
    private Map<String, Member> store = new HashMap<>();
    
    public void save(Member member) {
        store.put(member.getEmail(), member);
    }
}

public class JdbcMemberRepository {
    public void save(Member member) {
        // JDBC를 사용하여 데이터베이스에 저장
    }
}
```

**DIP 준수 예시:**
```java
// 저장소 인터페이스 정의
public interface MemberRepository {
    void save(Member member);
    Member findByEmail(String email);
}

// 인터페이스 구현체 1
public class MemoryMemberRepository implements MemberRepository {
    private Map<String, Member> store = new HashMap<>();
    
    @Override
    public void save(Member member) {
        store.put(member.getEmail(), member);
    }
    
    @Override
    public Member findByEmail(String email) {
        return store.get(email);
    }
}

// 인터페이스 구현체 2
public class JdbcMemberRepository implements MemberRepository {
    @Override
    public void save(Member member) {
        // JDBC를 사용하여 데이터베이스에 저장
    }
    
    @Override
    public Member findByEmail(String email) {
        // JDBC를 사용하여 데이터베이스에서 조회
        return null;
    }
}

// 인터페이스에 의존하는 서비스
public class MemberService {
    private final MemberRepository memberRepository;
    
    // 생성자를 통한 의존성 주입
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    
    public void signUp(String name, String email) {
        memberRepository.save(new Member(name, email));
    }
}

// 애플리케이션 설정
public class AppConfig {
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }
    
    public MemberRepository memberRepository() {
        // 필요에 따라 구현체 변경 가능
        // return new MemoryMemberRepository();
        return new JdbcMemberRepository();
    }
}
```

## 스프링과 객체지향

스프링은 다음과 같은 방식으로 객체지향 설계 원칙을 지원합니다:

- DI(의존성 주입) 컨테이너를 통해 다형성을 극대화
- OCP, DIP 원칙을 지키도록 지원
- 클라이언트 코드의 변경 없이 기능 확장 가능

## 의존성 주입 (DI)

스프링은 의존성 주입을 통해 OCP와 DIP 원칙을 지키도록 도와줍니다:

- 애플리케이션 실행 시점에 필요한 객체를 생성
- 생성자를 통해 의존관계를 주입
- 객체는 의존관계를 직접 생성하지 않고 외부에서 주입받음

### 스프링에서의 DI 예시:

```java
// 회원 저장소 인터페이스
public interface MemberRepository {
    void save(Member member);
    Member findById(Long id);
}

// 메모리 기반 구현체
@Repository
public class MemoryMemberRepository implements MemberRepository {
    private static Map<Long, Member> store = new HashMap<>();
    
    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }
    
    @Override
    public Member findById(Long id) {
        return store.get(id);
    }
}

// JPA 기반 구현체
@Repository
public class JpaMemberRepository implements MemberRepository {
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void save(Member member) {
        em.persist(member);
    }
    
    @Override
    public Member findById(Long id) {
        return em.find(Member.class, id);
    }
}

// 서비스 계층
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    
    // 생성자 주입
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    
    public Long join(Member member) {
        // 비즈니스 로직
        memberRepository.save(member);
        return member.getId();
    }
    
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}

// 스프링 설정
@Configuration
public class SpringConfig {
    
    @Bean
    public MemberRepository memberRepository() {
        // 구현체를 쉽게 변경할 수 있음
        // return new MemoryMemberRepository();
        return new JpaMemberRepository();
    }
    
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }
}
```

스프링 프레임워크는 이러한 의존성 주입을 통해 객체지향 설계 원칙을 쉽게 적용할 수 있도록 지원합니다. 개발자는 비즈니스 로직에 집중할 수 있으며, 코드의 재사용성과 테스트 용이성이 크게 향상됩니다.
