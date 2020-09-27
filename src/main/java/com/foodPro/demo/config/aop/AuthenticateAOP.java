package com.foodPro.demo.config.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Aspect //포인트컷 설정
@Order(Ordered.LOWEST_PRECEDENCE)
@Slf4j
public class AuthenticateAOP {


    /**
     * FUNCTION :: 로그인 인증 상태를 관리 하기 위한 AOP 설정
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    //Around 어노테이션으로 설정한경로의 클래스 파일들의 정보가 들어온다
    @Around("execution(* com.foodPro.demo..controller.*Controller.*(..))") //LINE :: 해당 패키지 경로의 컨트롤러에다가 모두적용시킨다.
    private Object loginStatusAOP(ProceedingJoinPoint joinPoint) throws Throwable {

        //LINE :: CONTROLLER 이름 가져오기
        String type = joinPoint.getSignature().getDeclaringTypeName();
        String class_name = type.substring(type.lastIndexOf(".") + 1);

        log.info("------------------------------  A  O  P  ------------------------------");
        log.info("Controller[" + class_name + " - " + joinPoint.getSignature().getName()+"]");



        //LINE :: 권한 체크
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("user auth :  " + authentication.getPrincipal() + "( admin or user )");


        if(authentication.getPrincipal().equals("anonymousUser")){
            log.info("Login Not Yet");
        }
        return joinPoint.proceed();
    }
}
