# HTTP 메시지 컨버터

@ResponseBody를 사용하면 HTTP BODY에 문자를 직접 반환하고, viewResolver 대신 HttpMessageConverter가 동작한다.

```
org.springframework.http.converter.HttpMessageConverter
```

'''java
public interface HttpMessageConverter<T> {
  boolean canRead(Class<?> clazz, @Nullable MediaType mediaType);
  boolean canWrite(Class<?> clazz, @Nullable MediaType mediaType);
  
  List<MediaType> getSupportedMediaTypes();
  
  T read(Class<? extends T> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException;
  void write(T t, @Nullable MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException;
 }
'''

- canRead(), conWrite() : 메시지 컨버터가 해당 클래스, 미디어타입을 지원하는지 체크
- read(), write() : 메시지를 읽고 쓰는 기능

메시지 컨버터 우선순위
0 = ByteArrayHttpMessageConverter
1 = StringHttpMessageConverter
2 = MappingJackson2HttpMessageConverter
.....

스프링 부트는 대상 클래스 타입과 미디어 타입 둘을 체크해서 사용여부를 결정한다. 만약 만족하지 않으면 다음 메시지 컨버터로 넘어간다.

- ByteArrayHttpMessageConverter : byte[] 처리
  1. 클래스타입 : byte[], 미디어타입 : */*
  ex) 요청 - @RequestBody byte[] data, 응답 - @ResponseBody return byte[] / 미디어타입 : application/octet-stream

- StringHttpMessageConverter : String 문자 데이터 처리
  1. 클래스타입 : String, 미디어타입 : */*
  ex) 요청 - @RequestBody String data, 응답 - @ResponseBody return "ok" / 미디어타입 : text/plain

- MappingJackson2HttpMessageConverter : application/json
  1. 클래스타입 : 객체 또는 HashMap, 미디어타입 : application/json
  ex) 요청 - @RequestBody HelloData data, 응답 - @ResponseBody return helloData / 미디어타입 : application/json
