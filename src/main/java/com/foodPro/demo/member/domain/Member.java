package com.foodPro.demo.member.domain;


import com.foodPro.demo.config.common.Address;
import com.foodPro.demo.config.security.Role;
import com.foodPro.demo.order.domain.Order;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column
    private String name; // LINE :: 이름

    @Column
    private int age;

    @Column
    private String pwd; // LINE :: 패스워드

    @Column
    private String low_pwd; // LINE :: 패스워드

    @Column
    private String email; // LINE :: 이메일 , UNIQUE 제약조건

    @Column
    private String birth; // LINE :: 생일

    @Column
    private String phone; // LINE :: 휴대폰 번호

    @Enumerated(EnumType.STRING)
    private Role role;          //LINE :: ADMIN : 관리자 / GUEST : 사용자

    @Embedded
    private Address address; // LINE :: 주소

    @OneToMany(mappedBy = "member", orphanRemoval = true) //LINE :: 주문목록 읽기전용 :: 회원정보가 삭제되면 order 정보도 같이 삭제된다.
    private List<Order> orderList = new ArrayList<>();


    /**
     * 회원 가입
     */
    @Builder
    public Member(String name, Address address, String email, String pwd, String birth, String phone,Role role,String low_pwd, int age) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.pwd = pwd;
        this.birth = birth;
        this.phone = phone;
        this.role = role;
        this.low_pwd = low_pwd;
        this.age = age;
    }

    // 비지니스 로직 :: 회원 정보 수정
    public Member update(String city, String street, String zipcode, String phone) {
        this.address = new Address(city, zipcode, street);
        this.phone = phone;
        return this;
    }

}

