package com.foodPro.demo.member.domain;


import com.foodPro.demo.config.common.Address;
import com.foodPro.demo.config.security.Role;
import com.foodPro.demo.order.domain.Orders;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor() //접근레벨 Protected 로 변경
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column
    private String email; // LINE :: 이메일 , UNIQUE 제약조건

    @Column
    private String pwd; // LINE :: 패스워드

    @Column
    private String low_pwd; // LINE :: 패스워드

    @Enumerated(EnumType.STRING)
    private Role role;          //LINE :: ADMIN : 관리자 / GUEST : 사용자

    @Embedded
    private Address address; // LINE :: 주소

    @OneToMany(mappedBy = "member", orphanRemoval = true) //LINE :: 주문목록 읽기전용 :: 회원정보가 삭제되면 order 정보도 같이 삭제된다.
    private List<Orders> orderList = new ArrayList<>();

    @Transient
    private boolean enabled;

    /**
     * 회원 가입
     */
    @Builder
    public Member(String email, Address address,String pwd,Role role,String low_pwd, boolean enabled) {
        this.address = address;
        this.email = email;
        this.pwd = pwd;
        this.role = role;
        this.low_pwd = low_pwd;
        this.enabled = enabled; // LINE :: USER 상태확인
    }

    // 비지니스 로직 :: 회원 정보 수정
    public Member update(String city, String street, String zipcode) {
        this.address = new Address(city, zipcode, street);
        return this;
    }

    public Member authorityUpdate(Role role){
        this.role = role;
        return this;
    }

//    public Member authority

}

