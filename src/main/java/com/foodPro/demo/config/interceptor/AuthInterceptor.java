package com.foodPro.demo.config.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
public class AuthInterceptor extends HandlerInterceptorAdapter {


    /**
     * FUNCTION :: SESSION 사용시 적용할 수 있는 인터셉터
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Object token = request.getHeader("Authorization");

        System.out.println("token = " + token);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //세션적용됨
//        log.info("aop authentication " + authentication.toString());
//        log.info("user auth : " + authentication.getAuthorities() +" user name : " + authentication.getName());

        if(authentication.getPrincipal().equals("anonymousUser")){
            response.sendRedirect("/error/unauthorized");
            return false;
        }
        return true;
    }
}
