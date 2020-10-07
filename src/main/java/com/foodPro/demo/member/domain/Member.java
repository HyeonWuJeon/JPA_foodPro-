package com.foodPro.demo.member.domain;


import com.foodPro.demo.config.common.Address;
import com.foodPro.demo.config.security.Role;
import com.foodPro.demo.order.domain.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column
    @Length(max = 15)
    @NotBlank
    private String name; // LINE :: 이름

    @Column
    @Length(max = 200)
    @NotBlank
    private String pwd; // LINE :: 패스워드

    @Column
    @Length(max = 200)
    @NotBlank
    private String email; // LINE :: 이메일 , UNIQUE 제약조건

    @Column
    @Length(max = 11)
    @NotBlank
    private String birth; // LINE :: 생일

    @Column
    @Length(max = 11)
    @NotBlank
    private String phone; // LINE :: 휴대폰 번호

    @Enumerated(EnumType.STRING)
    private Role role;          //LINE :: ADMIN : 관리자 / GUEST : 사용자

    @Embedded
    private Address address; // LINE :: 주소

    @OneToMany(mappedBy = "member") //LINE :: 주문목록 읽기전용
    private List<Order> orderList = new ArrayList<>();

    @Builder
    public Member(String name, Address address, String email, String pwd, String birth, String phone,Role role) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.pwd = pwd;
        this.birth = birth;
        this.phone = phone;
        this.role = role;
    }

    // 비지니스 로직 :: 회원 정보 수정
    public Member update(String city, String street, String zipcode, String phone) {
        this.address = new Address(city, zipcode, street);
        this.phone = phone;
        return this;
    }
}

