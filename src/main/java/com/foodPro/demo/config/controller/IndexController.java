package com.foodPro.demo.config.controller;

import com.foodPro.demo.config.filter.JwtTokenProvider;
import com.foodPro.demo.member.dto.MemberDto;
import com.foodPro.demo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final MemberService memberService;
    /**
     * FUNCTION :: 메인 화면
     * @return
     */
    @GetMapping(value = {"/","/main"})
    public String home(HttpServletResponse request)
    {
        System.out.println("request.getHeader(\"Authorization\") = " + request.getHeader("Authorization"));
        return "home";

    }
    /**
     * FUNCTION :: 로그인 페이지
     * @return
     */
    @GetMapping("/login")
    public String loginForm() {
        return "member/signin";
    }

    /**
     * FUNCTION :: 회원가입 폼
     * @param model
     * @return
     */
    @GetMapping("/signUp")
    public String signUpForm(Model model){
        model.addAttribute("memberForm", new MemberDto.Request());
        return "member/signup";
    }
    /**
     * FUNCTION :: 회원가입
     * @param
     * @return
     */
    @PostMapping(value =  "/api/member/signUp")
    public String signUpMember(@ModelAttribute("memberForm") @Valid MemberDto.Request request, BindingResult result){

        if(result.hasErrors()){
            return "member/signup";
        }
        return memberService.SignUp(request);
    }
}
