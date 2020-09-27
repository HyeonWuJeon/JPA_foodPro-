package com.foodPro.demo.member.domain;


import com.foodPro.demo.config.common.Address;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@RequiredArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column @Length(max = 15) @NotBlank
    private String name; // LINE :: 이름

    @Column @Length(max = 200) @NotBlank
    private String pwd; // LINE :: 패스워드

    @Column @Length(max = 200) @NotBlank
    private String email; // LINE :: 이메일ㅏ

    @Column @Length(max = 11) @NotBlank
    private String birth; // LINE :: 생일

    @Column @Length(max = 11) @NotBlank
    private String phone; // LINE :: 휴대폰 번호

    @Embedded
    private Address address; // LINE :: 주소

    @Builder
    public Member(String name, Address address, String email , String pwd, String birth, String phone) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.pwd = pwd;
        this.birth = birth;
        this.phone = phone;
    }
}
