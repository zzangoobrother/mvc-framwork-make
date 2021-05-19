package com.example.mvcframworkmake.mvc.framework.version4.Controller;

import com.example.mvcframworkmake.domain.Member;
import com.example.mvcframworkmake.domain.MemberRepository;

import java.util.List;
import java.util.Map;

public class MemberListControllerV4 implements ControllerV4 {

    private MemberRepository memberRepository = MemberRepository.getInstance();


    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        List<Member> members = memberRepository.findAll();

        model.put("members", members);

        return "members";
    }
}
