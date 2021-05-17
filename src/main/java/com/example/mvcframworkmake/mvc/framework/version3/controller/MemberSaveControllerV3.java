package com.example.mvcframworkmake.mvc.framework.version3.controller;

import com.example.mvcframworkmake.domain.Member;
import com.example.mvcframworkmake.domain.MemberRepository;
import com.example.mvcframworkmake.mvc.framework.ModelView;

import java.util.Map;

public class MemberSaveControllerV3 implements ControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {
        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));

        Member member = new Member(username, age);

        memberRepository.save(member);

        ModelView mv = new ModelView("save-result");
        mv.getModel().put("member", member);
        return mv;
    }
}
