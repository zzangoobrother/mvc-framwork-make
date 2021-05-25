# HTTP 요청 메시지 - 단순텍스트

HTTP 메시지 바디를 통해 데이터가 넘어오는 경우 @RequestParam, @ModelAttribute를 사용할 수 없다.

HTTP 메시지 바디의 데이터를 InputStream을 사용해서 직접 읽을 수 있다.
- InputStream : HTTP요청메시지 바디의 내용을 직접 조회
- OutputStream : HTTP응답메시지 바디에 직접 결과 출력

스프링 MVC는 HttpEntity를 지원한다.
메시지 바디 정보 직접 반환, 헤더 정보 포함, view X

스프링 MVC 내부에서 HTTP 메시지 바디를 문자나 객체로 변환하는데 이때 HTTP 메시지컨버터 라는 기능을 사용한다.

@RequestBody
- HTTP메시지 바디 정보를 조회, 헤더 정보는 HttpEntity를 사용하거나, @RequestHeader를 사용한다.

@ResponseBody
- 응답 결과를 HTTP메시지 바디에 직접 담아서 전달할 수 있다.

# HTTP 요청 메시지 - JSON

문자로 된 JSON데이터를 Jackson 라이브러리인 objectMapper를 사용하여 자바 객체로 변환한다.
@RequestBody를 사용해서 파라미터를 받고, objectMapper를 통해 자바 객체로 변환 가능하다.
@RequestBody 객체로 하면 객체를 지정하여 사용 가능하다.
ex) @RequestBody HelloData data

@RequestBody는 생략 불가능
- @ModelAttribute, @RequestParam은 생략 해서 사용 가능하지만 @RequestBody를 생략하면 @ModelAttribute가 적용된다.

@RequestBody 요청
- JSON요청 -> HTTP메시지컨버터 -> 객체

@ResponseBody 요청
- 객체 -> HTTP메시지컨버터 -> JSON응답
