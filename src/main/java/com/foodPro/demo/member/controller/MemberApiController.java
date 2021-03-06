package com.foodPro.demo.member.controller;

import com.foodPro.demo.member.dto.MemberDto;
import com.foodPro.demo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RequestMapping("/api/member")
@RestController
@RequiredArgsConstructor
public class MemberApiController {


    private final MemberService memberService;

    /**
     * FUNCTION :: 중복검사
     * @param request
     * @return
     */
    @PostMapping(value = "/checkEmail")
    public ResponseEntity checkEmail(@RequestBody MemberDto.Request request) {

        memberService.validateDuplicateMember(request.getEmail());
        return new ResponseEntity(HttpStatus.OK);
    }

}
