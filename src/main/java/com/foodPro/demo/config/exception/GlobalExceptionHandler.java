package com.foodPro.demo.config.exception;

import com.foodPro.demo.config.common.ApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler extends ApplicationService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * FUNCTION :: Checked 오류
     * @param e
     */
    @ExceptionHandler(Exception.class)
    public void globalException(Exception e) {
        log.info("Exception [" + e.getClass() + "] [" + e.getMessage() + "]");
        HashMap<String, Object> rtnMap = returnMap();
        rtnMap.put(AJAX_RESULT_TEXT, AJAX_RESULT_FAIL);
    }

    /**
     * FUNCTION :: Validation 오류
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public String ValidationException(ConstraintViolationException e) {
        log.info("Exception Validation [" + e.getClass() + "] [" + e.getMessage() + "]");
        HashMap<String, Object> rtnMap = returnMap();
        rtnMap.put(AJAX_RESULT_TEXT, AJAX_RESULT_ILLEGAL_STATE); //LINE :: 유효성 검증 오류
        return jsonFormatTransfer(rtnMap);
    }

    /**
     * FUNCTION :: Duplication 오류
     * @param e
     * @return
     */
    @ExceptionHandler(MemberDuplicationException.class)
    public String DuplicationException(MemberDuplicationException e) {
        log.info("Exception Validation [" + e.getClass() + "] [" + e.getMessage() + "]");
        HashMap<String, Object> rtnMap = returnMap();
        rtnMap.put(AJAX_RESULT_TEXT, AJAX_RESULT_DUPLICATE); //LINE :: 이메일 중복 오류
        return jsonFormatTransfer(rtnMap);
    }


}
