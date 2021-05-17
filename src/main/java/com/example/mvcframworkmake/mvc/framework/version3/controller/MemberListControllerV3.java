package com.example.mvcframworkmake.mvc.framework.version3.controller;

import com.example.mvcframworkmake.domain.Member;
import com.example.mvcframworkmake.domain.MemberRepository;
import com.example.mvcframworkmake.mvc.framework.ModelView;

import java.util.List;
import java.util.Map;

public class MemberListControllerV3 implements ControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {
        List<Member> members = memberRepository.findAll();

        ModelView mv = new ModelView("members");
        mv.getModel().put("members", members);
        return mv;
    }
}
