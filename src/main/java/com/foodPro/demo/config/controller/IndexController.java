package com.foodPro.demo.config.controller;

import com.foodPro.demo.config.filter.JwtTokenProvider;
import com.foodPro.demo.member.dto.MemberDto;
import com.foodPro.demo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class IndexController {

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
}
