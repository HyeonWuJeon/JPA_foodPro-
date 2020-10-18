package com.foodPro.demo.config.common;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Service
public class ApplicationService {

    public final Logger log = LoggerFactory.getLogger(this.getClass());

    public static final String AJAX_RESULT_TEXT = "httpCode";

    // LINE :: AJAX 결과 코드 ====================================================================================================================================================
    public static final String AJAX_RESULT_SUCCESS = "200";         // 성공
    public static final String AJAX_RESULT_DUPLICATE = "300";       // 중복
    public static final String AJAX_RESULT_NODATA = "400";          // 데이터 없음
    public static final String AJAX_RESULT_ILLEGAL_STATE = "401";   // 유효하지 않은 접근
    public static final String AJAX_RESULT_FAIL = "500";            // 실패
    public static final String AJAX_RESULT_OVERFLOW = "999";        // 다중 행 리턴
    public static final String AJAX_RESULT_ALREADY = "600";         // 이미 처리되어 있는 경우
    public static final String AJAX_RESULT_PAY_ALREADY = "601";     // 결제 이미 처리되어 있는 경우
    public static final String AJAX_RESULT_EDU_ALREADY = "602";     // 교육신청 이미 처리되어 있는 경우
    public static final String AJAX_RESULT_PAY_WAIT = "603";        // 결제 대기
    public static final String AJAX_RESULT_LOGIN_BLOCK = "LB";        // 결제 대기

    // LINE :: SESSION 데이터 처리 ====================================================================================================================================================
    @Autowired
    private HttpServletRequest request;

    private HttpSession getSession() {
        return request.getSession(true);
    }

    /**
     * FUNCTION :: Ajax요청에 대한 리턴 맵 객체 선언
     * @return
     */
    public HashMap<String, Object> returnMap(){
        HashMap<String, Object> rtnMap = new HashMap<>();
        rtnMap.put(AJAX_RESULT_TEXT, AJAX_RESULT_FAIL);      /* 실패 */
        return rtnMap;
    }

    /**
     * FUNCTION :: jSon 형식 변환
     *
     * @param map
     * @return String
     */
    public String jsonFormatTransfer(Map<String, Object> map) {
        String rtnJson = "";

        ObjectMapper mapper = new ObjectMapper();

        try {
            rtnJson = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            log.info("jsonFormatTransfer["+e.getMessage()+"]");
        }

        return rtnJson;
    }

    /**
     * Excel 다운로드
     */
    public void ExcelDown(){

    }

}
