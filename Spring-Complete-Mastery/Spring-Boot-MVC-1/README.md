# 섹션2. 서블릿
## 2.1 Project 만들기

- https://start.spring.io/
- 각종 설정들 
  - `Project` Gradle-Groovy
  - `Language` Java
  - `Spring Boot` 3.x.x
  - `Packaging` **_War_**
    - JSP 를 쓰려면 War 를 사용해야 함 
    - _일반적으로는 톰캣이 내장되어 바로 실행시킬 수 있는 Jar 를 사용하나, 강의에서는 예외로 War 사용함_  
  - `Java` 17
  - `Dependencies`
    - Lombok
    - Spring Web

## 2.2 ServletComponentScan 
```java
// 현재 내 패키지를 포함해서 하위 패키지를 다 뒤져서
// 자동으로 Servlet 을 등록할 수 있도록 해줌
@ServletComponentScan // * Servlet 자동 등록
@SpringBootApplication
public class ServletApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServletApplication.class, args);
	}
}
```
- 현재 내 패키지를 포함해서 하위 패키지를 다 뒤져서, 자동으로 Servlet 을 등록할 수 있도록 해줌

## 2.3 Servlet 만들기
```java
// Servlet 은 HttpServlet 을 상속받아야 함
@WebServlet(name = "helloServlet", urlPatterns = "/hello") // /hello 로 오면 요게 실행되는 것
public class HelloServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("request = " + request); // org.apache.catalina.connector.RequestFacade
        System.out.println("response = " + response); // org.apache.catalina.connector.ResponseFacade
        // RequestFacade 는 HttpServletRequest 에 대한 WAS 들의 구현체 중 하나. 지금은 Tomcat 을 쓰고 있으므로, RequestFacade 는 Tomcat 의 구현체
        // ResponseFacade 는 HttpServletResponse 에 대한 WAS 들의 구현체 중 하나. 지금은 Tomcat 을 쓰고 있으므로, ResponseFacade 는 Tomcat 의 구현체

        String username = request.getParameter("username");
        response.setContentType("text/plain"); // 보낼 데이터의 타입
        response.setCharacterEncoding("utf-8"); // 인코딩 정보, 옛날 시스템이 아니라면 UTF-8 을 서야 함
        response.getWriter().write("hello " + username); // body 에 담아서 보내줌
    }
}
```
- Servlet 은 HttpServlet 을 상속받아야 함
- RequestFacade 는 HttpServletRequest 에 대한 WAS 들의 구현체 중 하나. 지금은 Tomcat 을 쓰고 있으므로, RequestFacade 는 Tomcat 의 구현체
- ResponseFacade 는 HttpServletResponse 에 대한 WAS 들의 구현체 중 하나. 지금은 Tomcat 을 쓰고 있으므로, ResponseFacade 는 Tomcat 의 구현체

```java
String username = request.getParameter("username");
response.setContentType("text/plain"); // 보낼 데이터의 타입
response.setCharacterEncoding("utf-8"); // 인코딩 정보, 옛날 시스템이 아니라면 UTF-8 을 서야 함
response.getWriter().write("hello " + username); // body 에 담아서 보내줌
```

## 2.4 로깅
```shell
logging.level.org.apache.coyote.http11=debug
```
- application.properties
- http1.1 의 내용을 로깅하겠다는 의미 
### 적용하기 전 (before) 
```shell
request = org.apache.catalina.connector.RequestFacade@9c92855
response = org.apache.catalina.connector.ResponseFacade@6fef9a36
```

### 적용 후 (after)
```shell
Host: localhost:8080
Connection: keep-alive
Cache-Control: max-age=0
sec-ch-ua: "Whale";v="3", "Not-A.Brand";v="8", "Chromium";v="118"
sec-ch-ua-mobile: ?0
sec-ch-ua-platform: "macOS"
Upgrade-Insecure-Requests: 1
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Whale/3.23.214.17 Safari/537.36
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7
Sec-Fetch-Site: none
Sec-Fetch-Mode: navigate
Sec-Fetch-User: ?1
Sec-Fetch-Dest: document
Accept-Encoding: gzip, deflate, br
Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7

]
request = org.apache.catalina.connector.RequestFacade@73fefc15
response = org.apache.catalina.connector.ResponseFacade@1be14b67
```

## 2.5 HTTP 메시지 출력
### 메시지 출력 
```java
private static void printStartLine(HttpServletRequest request) {
    System.out.println("--- START LINE ---");
    System.out.println("request.getMethod() = " + request.getMethod()); // GET
    System.out.println("request.getProtocol() = " + request.getProtocol()); // HTTP/1.1
    System.out.println("request.getScheme() = " + request.getScheme()); // http
    System.out.println("request.getRequestURL() = " + request.getRequestURL()); // http://localhost:8080/request-header
    System.out.println("request.getRequestURI() = " + request.getRequestURI()); // /request-header
    System.out.println("request.getQueryString() = " + request.getQueryString()); // null
    System.out.println("request.isSecure() = " + request.isSecure()); // false (https 사용 유무)

    System.out.println("--- END LINE ---");
}
```
```shell
--- START LINE ---
request.getMethod() = GET
request.getProtocol() = HTTP/1.1
request.getScheme() = http
request.getRequestURL() = http://localhost:8080/request-header
request.getRequestURI() = /request-header
request.getQueryString() = name=jane
request.isSecure() = false
--- END LINE ---
```

### header 조회 (1)
```java
private static void printHeaders(HttpServletRequest request) {
    System.out.println("--- HEADERS START LINE ---");

    // 방법 1
    System.out.println("@방법 1");
    Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
        String headerName = headerNames.nextElement();
        System.out.println(headerName + ":" + request.getHeader(headerName));
    }

    // 방법 2
    System.out.println("@방법 2");
    request.getHeaderNames().asIterator()
            .forEachRemaining(headerName ->
                    System.out.println(headerName + ":" + request.getHeader(headerName))
            );


    System.out.println("--- HEADERS END LINE ---");
}
```

```shell
--- HEADERS START LINE ---
@방법 1
host:localhost:8080
connection:keep-alive
cache-control:max-age=0
sec-ch-ua:"Google Chrome";v="117", "Not;A=Brand";v="8", "Chromium";v="117"
sec-ch-ua-mobile:?0
sec-ch-ua-platform:"macOS"
upgrade-insecure-requests:1
user-agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36
accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7
sec-fetch-site:none
sec-fetch-mode:navigate
sec-fetch-user:?1
sec-fetch-dest:document
accept-encoding:gzip, deflate, br
accept-language:ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7
cookie:_ga=GA1.1.247559615.1693740907; _ga_ZF336Z6LWJ=GS1.1.1693740906.1.1.1693742918.0.0.0
@방법 2
host:localhost:8080
connection:keep-alive
cache-control:max-age=0
sec-ch-ua:"Google Chrome";v="117", "Not;A=Brand";v="8", "Chromium";v="117"
sec-ch-ua-mobile:?0
sec-ch-ua-platform:"macOS"
upgrade-insecure-requests:1
user-agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36
accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7
sec-fetch-site:none
sec-fetch-mode:navigate
sec-fetch-user:?1
sec-fetch-dest:document
accept-encoding:gzip, deflate, br
accept-language:ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7
cookie:_ga=GA1.1.247559615.1693740907; _ga_ZF336Z6LWJ=GS1.1.1693740906.1.1.1693742918.0.0.0
--- HEADERS END LINE ---
```

### header 조회 (2)
```java
private void printHeaderUtils(HttpServletRequest request) {
    System.out.println("--- Header 편의 조회 start ---");
    System.out.println("[Host 편의 조회]");
    System.out.println("request.getServerName() = " + request.getServerName()); // Host 헤더
    System.out.println("request.getServerPort() = " + request.getServerPort()); // Host 헤더 System.out.println();

    System.out.println();
    System.out.println("[Accept-Language 편의 조회]");
    request.getLocales().asIterator()
            .forEachRemaining(locale ->
                    System.out.println("locale = " + locale));
    System.out.println("request.getLocale() = " + request.getLocale());
    System.out.println();

    System.out.println("[cookie 편의 조회]");
    if (request.getCookies() != null) {
        for (Cookie cookie : request.getCookies()) {
            System.out.println(cookie.getName() + ": " + cookie.getValue());
        }
    }
    System.out.println();

    System.out.println("[Content 편의 조회]");
    System.out.println("request.getContentType() = " +
            request.getContentType());
    System.out.println("request.getContentLength() = " +
            request.getContentLength());
    System.out.println("request.getCharacterEncoding() = " +
            request.getCharacterEncoding());
    System.out.println("--- Header 편의 조회 end ---");
    System.out.println();
}
```
```shell
--- Header 편의 조회 start ---
[Host 편의 조회]
request.getServerName() = localhost
request.getServerPort() = 8080

[Accept-Language 편의 조회]
locale = ko_KR
locale = ko
locale = en_US
locale = en
request.getLocale() = ko_KR

[cookie 편의 조회]
_ga: GA1.1.247559615.1693740907
_ga_ZF336Z6LWJ: GS1.1.1693740906.1.1.1693742918.0.0.0

[Content 편의 조회]
request.getContentType() = null
request.getContentLength() = -1
request.getCharacterEncoding() = UTF-8
--- Header 편의 조회 end ---
```

### 기타 내용 조회 
```java
private void printEtc(HttpServletRequest request) {
    System.out.println("--- 기타 조회 start ---");

    System.out.println("[Remote 정보]");
    System.out.println("request.getRemoteHost() = " + request.getRemoteHost());
    System.out.println("request.getRemoteAddr() = " + request.getRemoteAddr());
    System.out.println("request.getRemotePort() = " + request.getRemotePort());
    System.out.println();

    System.out.println("[Local 정보]");
    System.out.println("request.getLocalName() = " + request.getLocalName());
    System.out.println("request.getLocalAddr() = " + request.getLocalAddr());
    System.out.println("request.getLocalPort() = " + request.getLocalPort());

    System.out.println("--- 기타 조회 end ---");
    System.out.println();
}
```
```shell
--- 기타 조회 start ---
[Remote 정보]
request.getRemoteHost() = 0:0:0:0:0:0:0:1
request.getRemoteAddr() = 0:0:0:0:0:0:0:1
request.getRemotePort() = 50349

[Local 정보]
request.getLocalName() = localhost
request.getLocalAddr() = 0:0:0:0:0:0:0:1
request.getLocalPort() = 8080
--- 기타 조회 end ---
```

## 2.6 HTML 요청 데이터
- HTTP 요청 메시지를 통해 `클라이언트 -> 서버` 로 데이터를 전달하는 방법에는 **주로 3가지** 가 있다
- **HttpServletRequest 를 사용하여 쉽게 읽을 수 있음**

### 1. 쿼리 파라미터 (ex. GET)
- body 없이 쿼리 파라미터에 데이터 포함해서 전달
  - `getParameter()` 로 조회 가능
- ex. 검색, 필터, 페이징등에서 많이 사용하는 방식

```shell
# 요청 URL
http://localhost:8080/request-param?username=name1&username=name100
```

```java
System.out.println("[전체 쿼리 파라미터 조회] - start");

System.out.println("@방법 1 - name 으로 조회");
request.getParameterNames().asIterator().forEachRemaining(paramName ->
        System.out.println("paramName = " + paramName + ":" + request.getParameter(paramName)));

System.out.println("@방법 2 - name 에 대응되는 값이 여러개인 경우");
// http://localhost:8080/request-param?username=name1&username=name100
String key = "username";
String[] usernames = request.getParameterValues(key);
for (String username : usernames) {
    System.out.println("username = " + username);
}

System.out.println("[전체 쿼리 파라미터 조회] - end");

response.getWriter().write("OK");
```

```shell
[전체 쿼리 파라미터 조회] - start
@방법 1 - name 으로 조회
paramName = username:name1
@방법 2 - name 에 대응되는 값이 여러개인 경우
username = name1
username = name100
[전체 쿼리 파라미터 조회] - end
```

```html
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body> <ul>
    <li><a href="basic.html">서블릿 basic</a></li> </ul>
</body>
</html>
```

<img width="470" alt="image" src="https://github.com/snaag/study-spring-mvc-1/assets/42943992/06ad2e86-c14b-4536-8ed2-8d2596069992">

<img width="1007" alt="image" src="https://github.com/snaag/study-spring-mvc-1/assets/42943992/9308ce38-f58b-4217-a477-a8be13587d0f">


### 2. HTML Form (ex. POST)
- `content-type: application/x-www-form-urlencoded`
- body 에 쿼리 파라미터 형식으로 전달
  - `getParameter()` 로 조회 가능 (1번과 동일)
- ex. 회원 가입, 상품 주문 등에서 HTML Form 의 형태로 사용
- (화면 예시는 1번과 동일)

### 3. HTTP message body 에 데이터를 직접 담아서 요청 
- HTTP API 에서 주로 사용 
- 데이터 형식으로는 주로 JSON 사용 (JSON, XML, TEXT 등) 
  - 과거에는 XML 을 주로 사용했으나, **최근에는 JSON** 이 표준이 됨

```java
@WebServlet(name = "requestBodyStringServlet", urlPatterns = "/request-body-string")
public class RequestBodyStringServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // * body -> bytecode -> string
        ServletInputStream inputStream = request.getInputStream(); // body 의 내용을 bytecode 로 얻을 수 있음 (body -> bytecode)
        // ! 이 때 encoding 명시 필수
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8); // bytecode 를 string 으로 변환함 (bytecode -> string)

        System.out.println("messageBody = " + messageBody);

        response.getWriter().write("ok");
    }
}
```

<img width="1004" alt="image" src="https://github.com/snaag/study-spring-mvc-1/assets/42943992/c10a4c09-ade6-4347-b24d-d36fe9f71a2d">

## 2.7 JSON 으로 데이터 주고받기
- 파싱을 위한 class 선언 필요

```java
@Getter @Setter
public class HelloData {
    private String username;
    private int age;
}
```

- Jackson 의 ObjectMapper 로 파싱
```java
// import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet(name = "requestBodyJsonServlet", urlPatterns = "/request-body-json")
public class RequestBodyJsonServlet extends HttpServlet {

    // * Jackson -> SpringBoot 에서 제공하는 Json library
    // * cf. Gson

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        System.out.println("messageBody = " + messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);

        System.out.println("helloData.username = " + helloData.getUsername());
        System.out.println("helloData.age = " + helloData.getAge());

        response.getWriter().write("ok");
    }
}
```

## 2.8 Header 보내기 
```java
@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // [status-line]
        response.setStatus(HttpServletResponse.SC_OK); // 200

        // [response-headers]
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // cache 무효화
        response.setHeader("Pragma","no-cache"); // 과거 버전의 캐시도 없앤다
        response.setHeader("my-header", "hello"); // 나의 커스텀 헤더

        // [Header 편의 메서드]
//        content(response);
//        cookie(response);
        redirect(response);

        // [message body]
        PrintWriter writer = response.getWriter();
        writer.write("안녕");
    }

    private void content(HttpServletResponse response) {
        // Content-Type: text/plain;charset=utf-8
        // Content-Length: 2

        // response.setHeader("Content-Type", "text/plain;charset=utf-8");
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
    }

    private void cookie(HttpServletResponse response) {
        // response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600");
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600); // 600초 (이 cookie 는 600초 동안 유효하다)

        response.addCookie(cookie);
    }

    private void redirect(HttpServletResponse response) throws IOException {
        // Status Code 302
        // Location: /basic/hello-form.html

        // response.setStatus(HttpServletResponse.SC_FOUND); // 302
        // response.setHeader("Location", "/basic/hello-form.html");
        response.sendRedirect("/basic/hello-form.html");
    }
}
```

- header 에 값을 set 할 떄 (`response.setHeader("xx","yy");`)
  - `response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");`
  - `response.setHeader("Pragma","no-cache");`
  - `response.setHeader("my-header", "hello");`
- 특정 header 를 set 할 때 
  - `리다이렉트` `response.sendRedirect("/basic/hello-form.html");`
  - `content type` `response.setContentType("text/plain");`
  - `content type` `response.setCharacterEncoding("utf-8");`
- cookie 를 set 할 때
  - `cookie.setMaxAge(600);`
  - `response.addCookie(cookie);`

<img width="900" alt="스크린샷 2023-12-29 오후 7 50 41" src="https://github.com/snaag/study-spring-mvc-1/assets/42943992/f489d2bb-c715-4903-ba3f-7df8325f88ec">

## 2.9 서블릿으로 HTML response 보내기
- header 
  - contentType 을 text/html 로 지정해야 함
  - 한글로 보내려면 charset 도 utf-8 로 해주어야 함 
- body
  - writer.println("<html>"); 으로 보낼 수 있음 

```java
@WebServlet(name="responseHtmlServlet", urlPatterns = "/response-html")
public class ResponseHtmlServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Content-Type: text/html;charset=utf-8;
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");

        PrintWriter writer = response.getWriter();
        writer.println("<html>");
        writer.println("<body>");
        writer.println("  <div>안녕</div>");
        writer.println("</body>");
        writer.println("</html>");
    }
}
```
<img width="900" alt="image" src="https://github.com/snaag/study-spring-mvc-1/assets/42943992/732fb9ce-2dbd-4406-9526-225d2ce28e5b">

## 2.10 서블릿으로 JSON response 보내기

- header
  - contentType 을 application/json 으로 지정해야 함
    - **application/json 은 스펙상 utf-8 형식을 지원하도록 되어있어, charset=utf-8 같은 추가 파라미터 지원 X**
- body
  - ObjectMapper 를 사용하여 string 으로 변환해서 보내기 

```java
@WebServlet(name="responseJsonServlet", urlPatterns = "/response-json")
public class ResponseJsonServlet extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Content-Type: application/json; charset=utf-8

        response.setContentType("application/json");
        // response.setCharacterEncoding("utf-8"); // 안써도 됨 

        // 1. 보낼 데이터 준비
        HelloData helloData = new HelloData();
        helloData.setUsername("kim");
        helloData.setAge(20);

        // 2. string 으로 보내기 (Jackson 의 ObjectMapper)
        // {"username": "kim", "age": 20}
        String result = objectMapper.writeValueAsString(helloData);
        response.getWriter().write(result);
    }
}
```

<img width="900" alt="image" src="https://github.com/snaag/study-spring-mvc-1/assets/42943992/c4a7acbd-6a4b-4e64-9321-7335c9a9cd44">

### (추가) 만약 Spring MVC 를 사용한다면?
```java
HelloData helloData = new HelloData();
helloData.setUsername("kim");
return helloData.setAge(20);
```

**이렇게만 해줘도 된다!**

# 섹션3. 서블릿, JSP, MVC 패턴 
## 3.1 회원가입 웹 애플리케이션 요구사항 안내 
- 기본 기능
  - 회원 조회 (Retrieve)
  - 회원 저장 (Create)
- 개발 순서 
  - core 모듈 -> 핵심 비즈니스 로직 
- 개선 순서  
  1. Servlet 으로 개발
  2. JSP 로 개발 
  3. MVC 패턴으로 개발

## 3.2 Member *C*.reate, *R*.etrieve 구현 

### Member class 생성 
- Lombok 의 @Getter, @Setter 를 사용하였으므로 Member class 의 getter, setter 를 별도로 만들지 않아도 됨  

```java
@Getter @Setter // Lombok 사용 
public class Member {
    private Long id;
    private String username;
    private int age;

    public Member() { }

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
```

### MemberRepository (구현) 클래스 구현
- 동시성 문제로 실무에서는 HashMap 대신 ConcurrentHashMap, Long 대신 AtomicLong 을 사용함
- singleton 
  - singleton 으로 만들기 위해 `private 생성자` 로 MemberRepository 를 선언하였음 
  - 외부에서 사용할 때에는 미리 만들어둔 `instance` 를 사용하도록 `getInstance()` 함수를 구현해둠  

```java
/**
 * 동시성 문제로 실무에서는 HashMap -> ConcurrentHashMap, Long -> AtomicLong 을 사용함
 */
public class MemberRepository {

    // private 생성자라서 static 하지 않아도 되지만, 일단 해두도록 함
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    // singleton 으로 만듦
    private static final MemberRepository instance = new MemberRepository();

    public static MemberRepository getInstance() {
        return instance;
    }

    // singleton 으로 만들 때에는 private 생성자를 만들어서, 외부에서 생성할 수 없도록 해야 함
    private MemberRepository() { }

    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public List<Member> findAll() {
        // 굳이 새로 선언해서 주는 이유는, store 를 보호하기 위해서임 (불변성?)
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
```

### MemberRepositoryTest 
- 테스트 작성 방법
  - `given` 이런게 주어졌을 때
  - `when` 이렇게 하였을 때
  - `then` 이런 결과가 나와야 해
- `@AfterEach`
  - 매 @Test 가 완료되면 수행되는 함수 

```java
class MemberRepositoryTest {

    // MemberRepository memberRepository = new MemberRepository(); // ! X ( private 생성자 이므로 안됨 )
    MemberRepository memberRepository = MemberRepository.getInstance();

    @AfterEach
    void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void save() {
        // given (이런게 주어졌을 때)
        Member member = new Member("hello", 20);

        // when (이렇게 하였을 때)
        Member savedMember = memberRepository.save(member);

        // then (이런 결과가 나와야 해)
        Member findMember = memberRepository.findById(savedMember.getId());
        assertThat(findMember).isEqualTo(savedMember);
    }

    @Test
    void findAll() {
        // given (이런게 주어졌을 때)
        Member member1 = new Member("member1", 20);
        Member member2 = new Member("member2", 30);

        memberRepository.save(member1);
        memberRepository.save(member2);

        // when (이렇게 하였을 때)
        List<Member> result = memberRepository.findAll();

        // then (이런 결과가 나와야 해)
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(member1, member2);
    }
}
```

## 3.3 [Servlet] 불편하게 회원 정보에 대한 동적인 HTML 반환하기
- Servlet 을 사용하여 동적인 HTML 을 client 에게 줄 수 있음
- 그러나 Servlet 에서 HTML 을 작성하는 것은 너무 불편하다! -> **템플릿엔진 (JSP `next!`, Thymeleaf 등) 사용**  

### 회원 정보 저장
- 저장 Form 

<img width="608" alt="image" src="https://github.com/snaag/study-spring-mvc-1/assets/42943992/7638425b-6cdd-4cd7-a2d7-1d96aa09612b">

```java
@WebServlet(name = "memberFormServlet", urlPatterns = "/servlet/members/new-form")
public class MemberFormServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");

        PrintWriter writer = response.getWriter();
        writer.write("""
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <title>Title</title>
                </head>
                <body>
                <form action="/servlet/members/save" method="post">
                    username: <input type="text" name="username" />
                    age:      <input type="text" name="age" />
                 <button type="submit">전송</button>
                </form>
                </body>
                </html>
                """);
        // ! servlet 으로 HTML 을 보내주려하면 상당히 불편하다

    }
}
```

- 저장 완료 페이지

<img width="463" alt="스크린샷 2024-01-02 오전 2 10 07" src="https://github.com/snaag/study-spring-mvc-1/assets/42943992/26f07e5b-ddb0-4d18-a2c1-13e8367852fa">

```java
@WebServlet(name = "memberSaveServlet", urlPatterns = "/servlet/members/save")
public class MemberSaveServlet extends HttpServlet {

    MemberRepository memberRepository = MemberRepository.getInstance();
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get 의 Parameter, Post 의 Form data 이든 상관 없음
        // 무조건 문자로 받음
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                " <meta charset=\"UTF-8\">\n" +
                "</head>\n" +
                "<body>\n" +
                "성공\n" +
                "<ul>\n" +
                "    <li>id="+member.getId()+"</li>\n" + // 동적으로 response 전송 가능
                "    <li>username="+member.getUsername()+"</li>\n" + // 동적으로 response 전송 가능
                "    <li>age="+member.getAge()+"</li>\n" + // 동적으로 response 전송 가능
                "</ul>\n" +
                "<a href=\"/index.html\">메인</a>\n" +
                "</body>\n" +
                "</html>");
    }
}
```

### 회원 정보 확인

<img width="379" alt="스크린샷 2024-01-02 오전 2 27 56" src="https://github.com/snaag/study-spring-mvc-1/assets/42943992/c8af8663-9de1-45e4-a944-34788b771fae">

```java
@WebServlet(name = "memberListServlet", urlPatterns = "/servlet/members")
public class MemberListServlet extends HttpServlet {

    MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Member> members = memberRepository.findAll();

        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");

        PrintWriter writer = response.getWriter();
        writer.write("<!DOCTYPE htm>\n");
        writer.write("<html>\n");
        writer.write("<head>\n");
        writer.write("  <meta charset=\"utf-8\">\n");
        writer.write("  <title>Title</title>\n");
        writer.write("</head>\n");
        writer.write("<body>\n");
        writer.write("<a href=\"/index.html\">메인</a>\n");
        writer.write("<table>\n");
        writer.write("  <thead>\n");
        writer.write("  <th>id</th>\n");
        writer.write("  <th>username</th>\n");
        writer.write("  <th>age</th>\n");
        writer.write("  </thead>\n");
        writer.write("  <tbody>\n");

        for (Member member : members) {
            writer.write("    <tr>\n");
            writer.write("      <td>" + member.getId() + "\n");
            writer.write("      <td>" + member.getUsername() + "\n");
            writer.write("      <td>" + member.getAge() + "\n");
            writer.write("    </tr>\n");
        }

        writer.write("  </tbody>\n");
        writer.write("</table>\n");
        writer.write("</body>\n");
        writer.write("</html>\n");
    }
}

```

## 3.4 [JSP] 덜 불편하게 회원 정보에 대한 동적인 HTML 반환하기

### 회원 정보 저장

- 저장 Form 

<img width="477" alt="스크린샷 2024-01-02 오전 2 49 58" src="https://github.com/snaag/study-spring-mvc-1/assets/42943992/a52eb5fd-8c33-4e30-8ae9-b3c0c3e675b5">

```jsp
<%-- JSP 라는 의미임 (이 줄이 꼭 있어야 됨) --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/jsp/members/save.jsp" method="post">
    username: <input type="text" name="username">
    age:      <input type="text" name="age">
    <button type="submit">전송</button>
</form>
</body>
</html>
```

- 저장 완료 페이지 

<img width="430" alt="스크린샷 2024-01-02 오전 2 55 23" src="https://github.com/snaag/study-spring-mvc-1/assets/42943992/00f483e4-f554-4839-a9f1-c1764e2eccb4">

```java
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- import --%>
<%@ page import="com.example.servlet.domain.member.MemberRepository" %>
<%@ page import="com.example.servlet.domain.member.Member" %>

<%-- MemberSaveServlet 에 있던 로직 --%>
<%
  System.out.println("save.jsp");

  MemberRepository memberRepository = MemberRepository.getInstance();
  // request, response 는 다른 import, 선언 없이 사용 가능
  String username = request.getParameter("username");
  int age = Integer.parseInt(request.getParameter("age"));

  Member member = new Member(username, age);
  memberRepository.save(member);
  System.out.println("member = " + member);
%>
<html>
<head>
  <title>Title</title>
</head>
<body>
성공
<ul>
  <li>id=<%= member.getId() %></li>
  <li>username=<%= member.getUsername() %></li>
  <li>age=<%= member.getAge() %></li>
</ul>
<a href="/index.html">메인</a>
</body>
</html>
```

### 회원 목록 확인

<img width="397" alt="스크린샷 2024-01-02 오전 2 58 34" src="https://github.com/snaag/study-spring-mvc-1/assets/42943992/27bb40f9-d2ba-452f-8acc-cedace56e821">

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.servlet.domain.member.MemberRepository" %>
<%@ page import="com.example.servlet.domain.member.Member" %>
<%@ page import="java.util.List" %>

<%-- MemberListServlet 에 있던 로직 --%>
<%
    MemberRepository memberRepository = MemberRepository.getInstance();
    List<Member> members = memberRepository.findAll();
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<a href="/index.html">메인</a>
<table>
    <thead>
    <th>id</th>
    <th>username</th>
    <th>age</th>
    </thead>
    <tbody>
    <%
        for (Member member : members) {
            out.write("<tr>\n");
            out.write("  <td>" + member.getId() + "</td>\n");
            out.write("  <td>" + member.getUsername() + "</td>\n");
            out.write("  <td>" + member.getAge() + "</td>\n");
            out.write("</tr>\n");
        }
    %>
    </tbody>
</table>
</body>
</html>
```

### JSP 사용 방법 
- 경로
  - webapp/jsp
  - `http://localhost:8080/jsp/xx.jsp`
- JSP 문법
  - JSP 사용 선언
    - `<%@ page contentType="text/html;charset=UTF-8" language="java" %>`
  - import
    - `<%@ page import="com.example.servlet.domain.member.MemberRepository" %>`
  - Java 코드 사용
    - `<% ... %>`

  
### 그럼에도 불편한... JSP 의 한계
- 코드가 지저분하고 복잡해짐 
  - 화면을 보여주는 부분 (view) 과 비즈니스 로직을 처리하는 부분이 한 페이지에 있어 **번거로움**
- 작업의 어려움 
  - 하나의 파일에 화면과 비즈니스 로직이 있으므로, 커밋 시 충돌도 잘 발생함
  - 유지보수도 어려워짐 

### 보다 개선된 MVC (Model, View, Controller) 패턴 
- 화면을 보여주는 로직, 비즈니스 로직을 분리하도록 함  
- 기존 프로젝트의 개선 방법 
  - Servlet 에서 비즈니스 로직을 돌리도록 함
  - JSP 에서 화면을 그리도록 함 

## 3.5 MVC 패턴 개요 
- 강점에 따른 역할 분리  
  - Servlet: **로직** 을 수행하는데 강점 
  - JSP: **화면을 렌더링** 하는데 강점

### MVC 패턴
- 컨트롤러 (Controller); Servlet
  - **HTTP 요청을 받아서 파라미터를 검증** 하고, **비즈니스 로직을 실행** 함
  - 그리고 **뷰에 전달할 결과 데이터를 조회해서 모델에 담음** 이 때 제어권은 Controller -> View 로 넘어감 
- 모델 (Model)
  - 뷰에 출력할 데이터를 담아둠
  - 뷰가 필요한 데이터를 모두 모델에 담아서 전달해주는 덕분에 뷰는 비즈니스 로직이나 데이터 접근을 몰라도 되고, 화면을 렌더링 하는 일에 집중할 수 있음
- 뷰 (View); JSP 
  - 모델에 담겨있는 데이터를 사용해서 화면을 그리는 일에 집중함 
  - 여기서는 HTML을 생성하는 부분을 말함 
  - **뷰는 꼭 HTML 페이지를 전달해야 하는 건 아님, XML, 등 여러 용도로 사용 가능** 
- 컨트롤러 <- 모델 -> 뷰 
  - 컨트롤러에서 뷰로 데이터를 전달할 때, **모델에 데이터를 담아서 전달함** 
  - 모델 덕분에 뷰 로직에서 데이터를 조회하는 **의존관계를 분리할 수 있음** 

### MVC, MVC 패턴 사용 전/후 비교
- AS-IS (MVC 패턴 미사용)

<img width="536" alt="image" src="https://github.com/snaag/study-spring-mvc-1/assets/42943992/01299ba3-2f8b-4df5-9b23-7284ca74e25b">

- TO-BE (MVC 패턴 사용)

(1)

<img width="539" alt="image" src="https://github.com/snaag/study-spring-mvc-1/assets/42943992/a9c22a24-a62d-455c-8bc2-235c9fca4584">

- 컨트롤러와 비즈니스 로직
  - 컨트롤러가 비즈니스 로직도 담당하고 있으나, **이러면 컨트롤러의 역할이 너무 많아짐**
  - 따라서 (2) 에서는 비즈니스 로직을 서비스, 리포지토리에서 구현하도록 함   

(2)

<img width="536" alt="image" src="https://github.com/snaag/study-spring-mvc-1/assets/42943992/b29cc3f8-3e53-417e-a78d-1b2170eadd5e">

- 컨트롤러
  - 요청이 들어온 파라미터를 확인하여, 제대로 된 요청이 맞는지 확인함
  - 제대로된 요청이 아니라면 400 등 에러를 반환함 
- 서비스, 리포지토리
  - 컨트롤러에서 문제가 없었다면, 여기서(서비스, 리포지토리) 비즈니스 로직, 데이터 접근을 처리함 
  - _(1) 에서는 Controller 가 비즈니스 로직도 담당하고 있음_
- 컨트롤러와 비즈니스 로직 
  - 비즈니스 로직을 변경하면 비즈니스 로직을 호출하는 컨트롤 러의 코드도 변경될 수 있음

## 3.6 MVC 패턴 적용  

- WEB-INF 디렉토리
  - WAS Server 의 convention
  - 외부에서 바로 호출되지 않음
    - controller 를 거쳐 내부에서 forward 해야만 호출됨 
    - 따라서 jsp 파일을 외부에서 직접적으로 부르지 않았으면 좋겠을 때 사용함 

```java
RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
dispatcher.forward(request, response); // 내부적으로, 서버에서 servlet -> JSP 호출
```

- redirect vs. forward
  - redirect (Server -> Client -> Server)
    - 리다이렉트는 실제 클라이언트(웹 브라우저)에 응답이 나갔다가, 클라이언트가 redirect 경로로 다시 요청하는 것
    - 따라서 클라이언트가 인지할 수 있고, URL 경로도 실제로 변경됨 
  - forward (Server -> Server)
    - 반면에 포워드는 서버 내부에서 일어나는 호출이기 때문에 클라이언트가 전혀 인지하지 못함 

### 회원 정보 저장

- 저장 Form

<img width="481" alt="스크린샷 2024-01-02 오전 5 07 44" src="https://github.com/snaag/study-spring-mvc-1/assets/42943992/df368d39-4d4b-4f4a-ad7e-30e71d075bf1">

<img width="424" alt="스크린샷 2024-01-02 오전 5 10 24" src="https://github.com/snaag/study-spring-mvc-1/assets/42943992/936f6b16-7028-4e60-bdc9-6f3fa1143316">



