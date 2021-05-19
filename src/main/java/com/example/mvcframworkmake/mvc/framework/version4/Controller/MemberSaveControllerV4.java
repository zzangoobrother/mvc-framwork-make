package com.example.mvcframworkmake.mvc.framework.version4.Controller;

import com.example.mvcframworkmake.domain.Member;
import com.example.mvcframworkmake.domain.MemberRepository;

import java.util.Map;

public class MemberSaveControllerV4 implements ControllerV4 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {

        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));

        Member member = new Member(username, age);

        memberRepository.save(member);

        model.put("member", member);

        return "save-result";
    }
}
