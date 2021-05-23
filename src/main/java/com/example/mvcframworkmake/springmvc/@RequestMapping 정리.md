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
