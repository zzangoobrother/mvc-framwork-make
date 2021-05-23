package com.example.mvcframworkmake.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/*
    @Controller는 반환값이 뷰 이름으로 인식되어 뷰가 랜더링 된다
    @RestController는 반환값이 HTTP메시지 바디에 바로 입력된다.
 */
@RestController
public class MappingController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/hello-basic", method = RequestMethod.GET)
    public String helloBasic() {
        log.info("helloBasic");
        return "ok";
    }

    @GetMapping("/hello-basic-v1")
    public String mappingGetV1() {
        log.info("helloBasic");
        return "ok";
    }

    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable() String userId) {
        log.info("mappingPath userId = {}", userId);
        return "ok";
    }

    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable Long orderId) {
        log.info("mappingPath userId ={}, orderId = {}", userId, orderId);
        return "ok";
    }

}
