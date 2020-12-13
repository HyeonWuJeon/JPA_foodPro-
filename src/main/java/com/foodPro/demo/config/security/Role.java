package com.foodPro.demo.config.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum Role {
    GUEST("ROLE_MEMBER","GUEST"), //LINE :: 회원
    ADMIN("ROLE_ADMIN","ADMIN"); //LINE :: 관리자



    private String value;
    private String title;
}
