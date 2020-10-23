package com.foodPro.demo.member.controller;

import com.foodPro.demo.config.common.pagging.PageWrapper;
import com.foodPro.demo.member.dto.MemberDto;
import com.foodPro.demo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String findAllDesc(Model model,Pageable pageable) {


        Page<MemberDto.Response> responses = memberService.findAllDesc(pageable);
        PageWrapper<MemberDto.Response> page = new PageWrapper<>(responses, "/admin/list");

        model.addAttribute("page", page);
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
