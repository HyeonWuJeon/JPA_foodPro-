package com.foodPro.demo.member.controller;

import com.foodPro.demo.config.common.pagging.PageWrapper;
import com.foodPro.demo.member.dto.MemberDto;
import com.foodPro.demo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/admin")
@Controller
//@RequiredArgsConstructor
public class AdminApiController {

//    private final MemberService memberService;


    @Autowired @Qualifier("memberService")
    private  MemberService memberService;

    /**
     * 사용자 정보 조회
     * @return
     */
    @GetMapping("/list")
    public String findAllDesc(Model model, @PageableDefault(size = 5) Pageable pageable, @RequestParam(value="age", defaultValue = "0") int age) {

        Page<MemberDto.Response> responses = memberService.findAllDesc(pageable, age);

        PageWrapper<MemberDto.Response> page = new PageWrapper<>(responses, "/admin/list");

        System.out.println("age = " + age);
        model.addAttribute("age",age);
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
