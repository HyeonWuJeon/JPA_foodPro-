package com.foodPro.demo.member.controller;

import com.foodPro.demo.member.dto.MemberDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/member")
@Controller
public class MemberController {

        @GetMapping("/user/findAll")
        public String findAll(){
            return "member/list";
        }
}
