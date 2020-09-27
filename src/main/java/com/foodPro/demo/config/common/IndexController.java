package com.foodPro.demo.config.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    /**
     * FUNCTION :: 메인 화면
     * @return
     */
    @GetMapping(value = {"/","/main"})
    public String home(){
        return "home";
    }
}
