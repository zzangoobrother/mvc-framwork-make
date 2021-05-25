1. @Controller
  - 반환값이 String 이면 뷰 이름 인식, 뷰 랜더링을 한다.

2. @RestController
  - HTTP메시지 바디에 바로 입력된다.

3. @RequestMapping
  - method 속성에 HTTP 메서드를 지정하지 않으면 GET, POST, PUT, DELETE 등 모두 허용된다.

4. @PathVariable
  - URL경로를 템플릿화 할 수 있는데, @PathVariable을 사용하여 매칭되는 부분을 편리하게 조회 가능, 이름과 파라미터 이름이 같으면 생략가능

5. @RequestParam
  - @RequestParam("username") String username => request.getParameter("username") 와 같다.
  - String, int, Integer 등 단순 타입이면 @RequestParam 생략 가능
  - @RequestParam(required = false) 파라미터 필수 여부, null 입력 가능, int age 는 int에 null 입력이 불가능하여 Integer로 변경해야 한다.
  
6. @ModelAttribute
  - 요청 파라미터를 바인딩 받을 객체를 자동화해서 값을 넣어준다
  - 요청 파라미터의 이름으로 객체 프로퍼티를 찾는다. 그리고 setter를 호출해서 파라미터 값을 바인딩 한다.
  - 숫자가 등러가야 할 곳에 문자를 넣으면 BindException이 발생한다.
  - 생략할 수 있다.
  - 생략 규칙 String, int, Integer 같은 단순 타입 -> @RequestParam, 나머지 -> @ModelAttribute
