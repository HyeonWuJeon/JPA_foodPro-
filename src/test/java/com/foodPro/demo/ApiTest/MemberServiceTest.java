package com.foodPro.demo.ApiTest;


import com.foodPro.demo.config.common.Address;
import com.foodPro.demo.config.exception.MemberDuplicationException;
import com.foodPro.demo.config.security.Role;
import com.foodPro.demo.member.domain.Member;
import com.foodPro.demo.member.dto.MemberDto;
import com.foodPro.demo.member.repository.MemberRepository;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    //given
    static String name = "현우";
    static String pwd = "12345";
    static String phone = "0109259";
    static String birth = "19951129";
    static String email = "yusa2@naver.com";
    static String city = "서울";
    static String zipcode = "330";
    static String street = "용마산로";
    static Role role = Role.ADMIN;


    @Test
    @Rollback(false)
    public void 회원가입(){
        //given
        Address address = new Address(city, zipcode, street);
        MemberDto.Request request = new MemberDto.Request(name,pwd, email,birth,phone,city,zipcode,street,address,role);
        //when
        memberService.SignUp(request);
        //then
        Member member = memberRepository.findById(1L).get();
        assertThat(member.getName()).isEqualTo(name);
    }


    @Test(expected = MemberDuplicationException.class)
    public void 이메일_중복검사(){

        memberService.validateDuplicateMember(email);

    }

    @Test
    @Rollback(false)
    public void 회원수정(){
        //given
        Address address = new Address("경기도", "광명", "00동");
        MemberDto.Request request = new MemberDto.Request().builder()
                .city(address.getCity())
                .street(address.getStreet())
                .zipcode(address.getZipcode())
                .phone("01012341234")
                .build();
        memberService.update(1L, request);

        Member member = memberRepository.findById(1L).get();
        assertThat(member.getAddress().getCity()).isEqualTo("경기도");

    }

}
