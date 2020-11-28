package com.foodPro.demo.config.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler{

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Handler :: MemberDuplicationException :: 회원가입 이메일 중복 오류
     * @param ex
     * @return
//     */
    @ExceptionHandler(MemberDuplicationException.class)
    public ResponseEntity DuplicationException(MemberDuplicationException ex, WebRequest request) {
        log.info("Exception Duplication [" + ex.getClass() + "] [" + ex.getMessage() + "]");
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
//        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST); //Body에 예외내용 담김.
        return new ResponseEntity(exceptionResponse, HttpStatus.valueOf(LoginErrorCode.DuplicateIdFound.getCode())); // 이메일 중복 6000 code
    }

    @ExceptionHandler(PasswordMissmatchException.class)
    public ResponseEntity PasswordMissmatchException (PasswordMissmatchException ex, WebRequest request) {
        log.info("[" + ex.getClass() + "] [" + ex.getMessage() + "]");
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.valueOf(LoginErrorCode.PasswordMisMatch.getCode())); // 6001 code
    }
    /**
     * Handler :: UserNotFoundException Error :: 정보 조회 오류
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity handleAllExceptions(UserNotFoundException ex, WebRequest request) {
        log.info("[" + ex.getClass() + "] [" + ex.getMessage() + "]");
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND); // 조회된 사용자 없음 404 code
    }

    /**
     * Handler :: Validation Error :: 회원가입 입력값 검증 오류 --> resultBinding 해결
     * @param ex
     * @param request
     * @return
     */
//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<Object> ValidationException(ConstraintViolationException ex, WebRequest request) {
//        log.info("Exception Validation [" + ex.getClass() + "] [" + ex.getMessage() + "]");
//        HashMap<String, Object> rtnMap = returnMap();
//        ExceptionResponse exceptionResponse =
//                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
//        return new ResponseEntity(exceptionResponse, HttpStatus.UNAUTHORIZED);
//    }




    /**
     * Handler :: Forbidden :: 권한 오류 접근불가
     * @param ex
     * @param request
     * @return
     */
//    @ExceptionHandler(MemberDuplicationException.class)
//    public ResponseEntity ForbiddenException(MemberDuplicationException ex, WebRequest request) {
//        log.info("Exception Duplication [" + ex.getClass() + "] [" + ex.getMessage() + "]");
//        ExceptionResponse exceptionResponse =
//                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
//        return new ResponseEntity(exceptionResponse, HttpStatus.valueOf(LoginErrorCode.UnrecognizedRole.getCode())); // 6002 권한 오류
//    }
}
