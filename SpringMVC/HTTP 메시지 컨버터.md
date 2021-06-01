# HTTP 메시지 컨버터

@ResponseBody를 사용하면 HTTP BODY에 문자를 직접 반환하고, viewResolver 대신 HttpMessageConverter가 동작한다.

```
org.springframework.http.converter.HttpMessageConverter
```

```java
public interface HttpMessageConverter<T> {
  boolean canRead(Class<?> clazz, @Nullable MediaType mediaType);
  boolean canWrite(Class<?> clazz, @Nullable MediaType mediaType);
  
  List<MediaType> getSupportedMediaTypes();
  
  T read(Class<? extends T> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException;
  void write(T t, @Nullable MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException;
 }
```

- canRead(), conWrite() : 메시지 컨버터가 해당 클래스, 미디어타입을 지원하는지 체크
- read(), write() : 메시지를 읽고 쓰는 기능

메시지 컨버터 우선순위
0 = ByteArrayHttpMessageConverter
1 = StringHttpMessageConverter
2 = MappingJackson2HttpMessageConverter
.....

스프링 부트는 대상 클래스 타입과 미디어 타입 둘을 체크해서 사용여부를 결정한다. 만약 만족하지 않으면 다음 메시지 컨버터로 넘어간다.

- ByteArrayHttpMessageConverter : byte[] 처리
  1. 클래스타입 : byte[], 미디어타입 : `*`/`*`   
  ex) 요청 - @RequestBody byte[] data, 응답 - @ResponseBody return byte[] / 미디어타입 : application/octet-stream

- StringHttpMessageConverter : String 문자 데이터 처리
  1. 클래스타입 : String, 미디어타입 : `*`/`*`   
  ex) 요청 - @RequestBody String data, 응답 - @ResponseBody return "ok" / 미디어타입 : text/plain

- MappingJackson2HttpMessageConverter : application/json
  1. 클래스타입 : 객체 또는 HashMap, 미디어타입 : application/json   
  ex) 요청 - @RequestBody HelloData data, 응답 - @ResponseBody return helloData / 미디어타입 : application/json
  
StringHttpMessageConverter 예제
```
content-type : application/json
  
@RequestMapping
void hello(@RequestsBody String data) {}
```

MappingJackson2HttpMessageConverter 예제
```
content-type : application/json

@RequestMapping
void hello(@RequestsBody HelloData data) {}
```

### HTTP 요청 데이터 읽기
- 컨트롤러에서 @RequestBody, HttpEntity 파라미터를 사용한다.
- 메시지 컨버터가 canRead()를 호출한다.
  - 대상 클래스 타입인가? - byte[], String, HelloData
  - Content-Type 미디어 타입을 지원하는가? - text/plain, application/json
- canRead()를 만족하면 read() 호출하여 객체 생성하고, 반환한다.

### HTTP 응답 데이터 생성
- 컨트롤러에서 @ResponseBody, HttpEntity로 반환
- 메시지 컨버터가 canWrite() 호출
  - 대상 클래스 타입인가? - byte[], String, HelloData
  - Accept 미디어 타입을 지원하는가? - text/plain, application/json
- canWrite()를 만족하면, write() 호출하여 HTTP 응답 메시지 바디에 데이터를 생성한다.


# 요청 매핑 핸들러 어뎁터 구조
HTTP 메시지 컨버터는 @RequestMapping을 처리하는 RequestMappingHandlerAdapter에 있다.

![캡처8](https://user-images.githubusercontent.com/42162127/120189605-876b6000-c252-11eb-86ce-597bd8801335.PNG)

컨트롤러는 다양한 파라미터를 사용한다.
HttpServletRequest, Model, @RequestParam, @ModelAttribute, @RequestBody, HttpEntity 등 애노테이션, HTTP 메시지를 처리하는 부분까지 유연하게 동작한다.
이렇게 유연하게 처리할 수 있는 이유는 ArgumentResolver 덕분이다.
RequestMappingHandlerAdaptor는 ArgumentResolver를 호출하여 다양한 파라미터의 값을 생성하고, 준비되면 컨트롤러를 호출하여 값을 넘겨준다.

### HandlerMethodArgumentResolver
```java
public interface HandlerMethodArgumentResolver {
  boolean supportsParameter(MethodParameter parameter);
  
  @Nullable
  Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer, NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception;
}
```
supportsParameter()를 호출, 해당 파라미터를 지원하는지 체크하고, 지원하면 resolveArgument()를 호출해서 객체를 생성한다. 생성된 객체가 컨트롤러 로 넘어간다.

### HandlerMethodReturnValueHandler
응답 값을 변환하고 처리한다. String으로 뷰 이름을 반환해도 동작하는 이유이다.

![캡처9](https://user-images.githubusercontent.com/42162127/120194963-34e17200-c259-11eb-90cb-3934fcc4faaa.PNG)

요청의 경우 @RequestBody를 처리하는 ArgumentResolver가 있고, HttpEntity를 처리하는 ArgumentResolver가 있다. HTTP 메시지 컨버터를 사용해서 필요한 객체를 생성한다.

응답의 경우 @ResponseBody와 HttpEntity를 처리하는 ReturnValueHandler가 있다. 여기에서 HTTP 메시지 컨버터를 호출해서 응답 결과를 만든다.
