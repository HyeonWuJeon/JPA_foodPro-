package com.foodPro.demo.config.interceptor;

import com.foodPro.demo.config.common.AuthConstants;
import com.foodPro.demo.config.filter.JwtTokenProvider;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenInterceptor implements HandlerInterceptor {

    /**
     * FUNCTION :: 토큰사용시 적용할 수 있는 인터셉터
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws IOException
     * @throws IOException
     */
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