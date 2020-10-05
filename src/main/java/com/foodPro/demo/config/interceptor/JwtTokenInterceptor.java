package com.foodPro.demo.config.interceptor;

import com.foodPro.demo.config.common.AuthConstants;
import com.foodPro.demo.config.filter.JwtTokenProvider;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 토큰 유효성 검사
 * 유효성검사에 실패할 경우 에러페이지로 이동
 */
public class JwtTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException, IOException {
        String header = request.getHeader(AuthConstants.AUTH_HEADER);

        if (header != null) {
            String token = JwtTokenProvider.getTokenFromHeader(header);
            if (JwtTokenProvider.isValidToken(token)) { // LINE :: 토큰 유효성 검사
                return true;
            }
        }
        response.sendRedirect("/error/unauthorized");
        return false;
    }
}