package com.foodPro.demo.ApiTest;


import com.foodPro.demo.config.common.Address;
import com.foodPro.demo.member.domain.Member;
import com.foodPro.demo.member.dto.MemberDto;
import com.foodPro.demo.member.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @PersistenceContext
    EntityManager em;
    //given
    static String name = "현우";
    static String pwd = "12345";
    static String phone = "0109259";
    static String birth = "19951129";
    static String email = "test@gmail.com";
    static String city = "서울";
    static String zipcode = "330";
    static String street = "용마산로";

    @Test
    @Rollback(false)
    public void 회원가입(){
        //when
        memberService.SignUp(MemberDto.Request.builder()
                .address(new Address(city, zipcode, street))
                .email(email)
                .name(name)
                .birth(birth)
                .city(city)
                .pwd(pwd)
                .street(street)
                .phone(phone)
                .build());
        //then
        assertThat(em.find(Member.class, 1L).getName()).isEqualTo(name);
        // 이메일 중복일 경우
        fail("이메일 중복 예외");
    }

    @Test(expected = ConstraintViolationException.class)
    public void 유효성검사(){
        //when
        memberService.SignUp(MemberDto.Request.builder()
                .address(new Address(city, zipcode, street))
                .email(email)
                .name(null)
                .birth(birth)
                .city(city)
                .pwd(pwd)
                .street(street)
                .phone(phone)
                .build());

        fail("예외가 발생해야 한다");
    }


}
