package com.foodPro.demo.config.common;

import com.foodPro.demo.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

/**
 * 3. UsernamePasswordToken을 Authentication Manager 로 전달
 * AuthenticationFilter(인증 필터) 는 생성한 UsernamePasswordToken을 AuthenticationManager에게 전달한다.
 * AuthenticationManager은 실제로 인증을 처리할 여러 개의 AuthenticationProvider를 가지고 있다.
 *
 * 실제 인증과정을 수행한다.
 */
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Resource(name = "memberService")
    private UserDetailsService userDetailsService;

    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * FUNCTION :: 실제 인증과정을 수행 (principal : email / credentials : pwd)
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        /**
         * 4.UsernamePasswordToken을 AuthenticationProvider에게 전달하여 정보 조회.
         * - 스프링 시큐리티에서 eamil로 디비에서 데이터를 조회한 다음 비밀번호 일치 여부를 검사하는 방식으로 작동한다.
         * - 비밀번호가 일치할 경우 AuthenticationFilter 로 인증 정보를 던져준다.
         */
        String userEmail = token.getName();
        String userPw = (String) token.getCredentials();
        MemberDto.Response userDetailsVO = (MemberDto.Response) userDetailsService.loadUserByUsername(userEmail);

        if (!passwordEncoder.matches(userPw, userDetailsVO.getPwd())) { //LINE : 패스워드 확인
            throw new BadCredentialsException(userDetailsVO.getUsername() + "Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetailsVO, userPw, userDetailsVO.getAuthorities()); //토큰 반환
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
