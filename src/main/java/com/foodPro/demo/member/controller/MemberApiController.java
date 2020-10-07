package com.foodPro.demo.member.controller;

import com.foodPro.demo.config.common.ApplicationService;
import com.foodPro.demo.member.dto.MemberDto;
import com.foodPro.demo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@RequestMapping("/api/member")
@RestController
@RequiredArgsConstructor
public class MemberApiController extends ApplicationService {

    private final MemberService memberService;

    /**
     * FUNCTION :: 회원가입
     * @param request
     * @return
     */
    @PostMapping(value =  "/signUp")
    public String signUpMember(@Valid MemberDto.Request request){
        return memberService.SignUp(request);
    }

    /**
     * FUNCTION :: 중복검사
     * @param request
     * @return
     */
    @PostMapping(value = "/checkEmail")
    public String checkEmail(MemberDto.Request request) {
        HashMap<String, Object> rtnMap = returnMap();
        memberService.validateDuplicateMember(request.getEmail());
        rtnMap.put(AJAX_RESULT_TEXT, AJAX_RESULT_SUCCESS);
        return jsonFormatTransfer(rtnMap);
    }
}
