package com.foodPro.demo.config.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN","ADMIN"), //LINE :: 관리자
    GUEST("ROLE_MEMBER","GUEST"); //LINE :: 회원

    private String value;
    private String title;
}
