package com.foodPro.demo.config.common;

import com.foodPro.demo.config.filter.JwtTokenProvider;
import com.foodPro.demo.member.dto.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 2.AuthenticationProvider.class 를 통해 인증이 성공될 경우 처리된다.
 * 로그인이 성공될 경우 토큰을 생성하고 response에 추가하여 반환한다.
 */
@Slf4j
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        MemberDto.Response member = ((MemberDto.Response) authentication.getPrincipal());
        String token = JwtTokenProvider.createToken(member.getEmail());

//        request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        HttpSession session = request.getSession();
//        session.setAttribute(AuthConstants.AUTH_HEADER, token);
        response.addHeader(AuthConstants.AUTH_HEADER,  AuthConstants.TOKEN_TYPE + " " + token); //LINE :: 토큰을 헤더에 실어 보낸다.
    }
}