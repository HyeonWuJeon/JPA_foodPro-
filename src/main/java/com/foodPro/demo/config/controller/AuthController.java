package com.foodPro.demo.config.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    /**
     * FUNCTION :: 로그인 페이지
     * @return
     */
    @GetMapping("/login")
    public String loginForm(){
        return "login";
    }
}
