package com.example.mvcframworkmake.mvc.framework.version3.controller;

import com.example.mvcframworkmake.mvc.framework.ModelView;

import java.util.Map;

public interface ControllerV3 {
    ModelView process(Map<String, String> paramMap);
}
