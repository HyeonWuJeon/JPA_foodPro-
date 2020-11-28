package com.foodPro.demo.config.exception;

public enum LoginErrorCode {
//    OperationNotAuthorized(6000,"Operation not authorized"),
    DuplicateIdFound(6000,"Duplicate EMAIL"), // 이메일 중복
    PasswordMisMatch(6001,"Password Mismatch"), // 패스워드 불일치
    UnrecognizedRole(6002,"Unrecognized Role"); // 권한 없음

    private int code;
    private String description;
    private LoginErrorCode(int code, String description) { this.code = code; this.description = description; }
    public int getCode() { return code; }
    public String getDescription() { return description; }
}

