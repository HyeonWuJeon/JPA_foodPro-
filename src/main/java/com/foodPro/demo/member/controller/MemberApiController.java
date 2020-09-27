package com.foodPro.demo.member.controller;

import com.foodPro.demo.member.dto.MemberDto;
import com.foodPro.demo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/api/member")
@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    @PostMapping("/signUp")
    public String signUpMember(@Valid MemberDto.Request request){
        System.out.println("request.getName() = " + request.getName());
        System.out.println("request.getPwd() = " + request.getPwd());
        System.out.println("request.getBirth() = " + request.getBirth());
        System.out.println("request.getCity() = " + request.getCity());
        System.out.println("request.getEmail() = " + request.getEmail());
        System.out.println("request.getPhone() = " + request.getPhone());
        return memberService.SignUp(request);
    }
}
