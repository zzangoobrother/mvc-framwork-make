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
