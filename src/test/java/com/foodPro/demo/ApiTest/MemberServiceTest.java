package com.foodPro.demo.ApiTest;


import com.foodPro.demo.config.common.Address;
import com.foodPro.demo.member.domain.Member;
import com.foodPro.demo.member.dto.MemberDto;
import com.foodPro.demo.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(false)
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @PersistenceContext
    EntityManager em;

    @Test
    public void 회원가입(){

        //given
        String name = "현우";
        String pwd = "12345";
        String phone = "0109259";
        String birth = "19951129";
        String email = "test@gmail.com";
        String city = "서울";
        String zipcode = "330";
        String street = "용마산로";

        //when
        memberService.SignUp(MemberDto.Request.builder()
                .address(new Address(city, zipcode, street))
                .email(email)
                .name(name)
                .birth(birth)
                .city(city)
                .pwd(phone)
                .street(street)
                .phone(phone)
                .build());

        //then
        assertThat(em.find(Member.class, 1L).getName()).isEqualTo(name);

    }
}
