package com.example.mvcframworkmake.springmvc.version1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SpringMemberFormControllerV1 {

    @RequestMapping("/springmvc/version1/members/new-form")
    public ModelAndView process() {
        return new ModelAndView("new-form");
    }
}
