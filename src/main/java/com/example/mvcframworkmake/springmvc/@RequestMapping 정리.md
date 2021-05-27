# @RequestMapping
- RequestMappingHandlerMapping
- RequestMappingHandlerAdqpter
애노체이션 기반의 컨트롤러를 지원하는 핸들러 매핑과 어댑터

1. @Controller
- 스프링이 자동으로 스프링 빈으로 등록(내부에 @Component 애노테이션이 있어 컴포넌트스캔 대상)

2. @RequestMapping
- 요청 정보 매핑, URL이 호출되면 이 메서드가 호출

3. ModelAndView
- 모델과 뷰 정보를 담아서 반환

# HTTP응답 - 정적 리소스, 뷰 템플릿

- 정적 리소스 : 정적인 HTML, css, js 제공
- 뷰 템플릿 : 동적인 HTML
- HTTP 메시지 : HTTP 메시지 바디에 JSON 같은 데이터

1. 정적 리소스
 - src/main/resources/static 경로로 파일을 변경 없이 그대로 서비스한다.

2. 뷰 템플릿
 - src/main/resources/templates 경로를 가진다.

View vs HTTP메시지
@ResponseBody가 없으면 뷰리졸버가 실행, @ResponseBody가 있으면 뷰리졸버가 실해아지 않고, HTTP 메시지 바디에 문자가 입력된다.

# HTTP API, 메시지 바디에 직접 입력
```java
@GetMapping
public void v1(HttpServletResponse response) throws IOException {
  response.getWriter().write("ok");
}

@GetMapping
public ResponseEntity<String> v2() {
 return new ResponseEntity<>(ok", HttpStatus.OK);
}

@ResponseBody
@GetMapping
public String V3() {
 return "ok";
}

@GetMapping
public ResponseEntity<HelloData> v4() {
 HelloData helloData = new HelloData();
 helloData.setUsername("userA");
 helloData.setAge(20);
 return new ResponseEntity<>(helloData, HttpStatus.OK);
}

@ResponseStatus(HttpStatus.OK)
@ResponseBody
@GetMapping
public HelloData v5() {
 HelloData helloData = new HelloData();
 helloData.setUsername("userA");
 helloData.setAge(20);
 return helloData;
}
```
1. v1
 - HttpServletResponse 객체를 통해 HTTP 메시지 바디에 응답 전달

2. v2
 - HttpEntity를 상속 받는 ResponseEntity로 응답

3. v3
 - @ResponseBody를 사용하여 HTTP 메시지 컨버터를 통해 HTTP 메시지 직접 입력

4. v4
 - ResponseEntity를 반환하여 HTTP메시지 컨버터를 통해 HSON 형식으로 변환하여 응답

5. v5
 - return을 객체로 응답하면 상태코드 응답을 위한 @ResponseStatus 애노테이션을 사용해야 한다.
