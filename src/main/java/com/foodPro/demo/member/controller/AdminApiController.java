package com.foodPro.demo.member.controller;

import com.foodPro.demo.member.dto.MemberDto;
import com.foodPro.demo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
public class AdminApiController {

    private final MemberService memberService;

    /**
     * 사용자 정보 조회
     * @return
     */
    @GetMapping("/list")
    public String findAll(Model model) {
        List<MemberDto.Response> responses = memberService.findAllDesc();
        model.addAttribute("list", responses);
        return "member/list";
    }

    /**
     * 사용자 개인 정보 조회
     */
    @GetMapping("/member/settings/{id}")
    public String updateMember(@PathVariable Long id, Model model) {
        MemberDto.Response dto = memberService.findById(id);
        model.addAttribute("member", dto);
        return "member/settings";
    }
}
