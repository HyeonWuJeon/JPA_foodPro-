
package com.foodPro.demo.config.controller;

import com.foodPro.demo.member.dto.MemberDto;
import com.foodPro.demo.member.service.MemberService;
import com.foodPro.demo.member.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

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
    public String loginForm(@RequestParam(defaultValue = "false") Boolean error, Model model) {
        if(error){
            model.addAttribute("errorMessage", "아이디나 패스워드가 확인되지 않습니다.");
        }
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
        memberService.SignUp(request);
        return "redirect:/login";
    }

    @GetMapping(value ="/accessDenied")
    public String denied(ServletResponse response) throws IOException {
        return "accessDenied";
    }
}
