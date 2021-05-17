package com.example.mvcframworkmake.mvc.framework.version3.controller;

import com.example.mvcframworkmake.mvc.framework.ModelView;

import java.util.Map;

public class MemberFormControllerV3 implements ControllerV3 {
    @Override
    public ModelView process(Map<String, String> paramMap) {
        return new ModelView("new-form");
    }
}
