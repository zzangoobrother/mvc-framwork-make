# SpringMVC 구조 정리

![캡처6](https://user-images.githubusercontent.com/42162127/118816266-f52b9980-b8ec-11eb-9702-32c3fbfcb3aa.PNG)

스프링 MVC는 프론트 컨트롤러 패턴으로 구현되어 있다.
그리고 프론트 컨트롤러가 DispatcherServlet 이다.

org.springframework.web.servlet.DispatcherServlet

DispatcherServlet도 부모 클래스로 HttpServlet을 상속 받아 사용하고, 동작한다.

![캡처7](https://user-images.githubusercontent.com/42162127/118817094-d083f180-b8ed-11eb-85bc-2bf9f4b55b07.PNG)

위 그림은 DispatcherServlet의 다이어그램이다.
DispatcherServlet -> FrameworkServlet -> HttpServletBean -> HttpServlet
으로 상속받는다.

HttpServlet의 service() 호출로 FrameworkServlet.service()가 시작된다.
여러 메서드가 시작되면서 DispatcherServlet.doService()가 호출되고
DispatcherServlet의 핵심인 doDispatch() 코드가 실행된다.
doDispatch()에 SpringMVC의 모든 구조가 다 들이 있다.

DispatcherServlet.doDispatch()
```java
protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
  HttpServletRequest processedRequest = request;
  HandlerExecutionChain mappedHandler = null;
  
  try {
    ModelAndView mv = null;
    
    try {
      // 1. 핸들러 조회
      mappedHandler = getHandler(processedRequest);
      if (mappedHandler == null) {
        noHandlerFound(processedRequest, response);
        return;
      }
      
      // 2. 핸들러를 처리할 수 있는 핸들러 어댑터 조회
      HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());
      
      // 3. 핸들러 어댑터 실행 -> 4. 핸들러 어댑터를 통해 핸들러 실행 -> 5. ModelAndView 반환
      mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
      
      processDispatchResult(processedRequest, response, mappedHandler, mv, dispatchException);
    }
  }
}

private void processDispatchResult(HttpServletRequest request, HttpServletResponse response, @Nullable HandlerExecutionChain mappedHandler, @Nullable ModelAndView mv, @Nullable Exception exception) throws Exception {
  if (mv != null && !mv.wasCleared()) {
    // view 랜더링 호출
    render(mv, request, response);
  }
}

protected void render(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) throws Exception {
  View view;
  String viewName = mv.getViewName();
  
  if (viewName != null) {
    // 6. view 리졸버를 통해 view 찾기 -> 7. view 반환
    view = resolveViewName(viewName, mv.getModelInternal(), locale, request);
  }
  
  // 8. view 렌더링
  view.render(mv.getModelInternal(), request, response);
}
```
- 동작순서
1. 핸들러 조회 : URL에 매핑된 핸들러 조회
2. 핸들러 어댑터 조회 : 핸들러를 실행할 수 있는 어댑터 조회
3. 핸들러 어댑터 실행 : 핸들러 어댑터 실행
4. 핸들러 실행 : 핸들러 어댑터가 실제 핸들러 실행
5. ModelAndView 반환 : 핸들러 어댑터가 반환하는 정보를 ModelAndView로 변환해서 반환
6. viewResolver 호출 : viewResolver 찾고 실행
7. view 반환 : view 논리 이름을 물리 이름으로 바꾸고, 렌더링 역할을 담당하는 view 객체를 반환
8. view 렌더링 : view 렌더링

스프링MVC의 큰 강점은 DispatcherServlet 코드의 변경 없이 원하는 기능을 변경하거나 확장할 수 있다는 것이다.
대부분을 확장 가능하게 인터페이스로 제공한다.
