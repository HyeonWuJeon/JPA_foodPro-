package com.foodPro.demo.Service;


import com.foodPro.demo.config.common.Address;
import com.foodPro.demo.config.security.Role;
import com.foodPro.demo.member.domain.Member;
import com.foodPro.demo.member.dto.MemberDto;
import com.foodPro.demo.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public class UserTestHelper {


    private final MemberService memberService;
    public UserTestHelper(MemberService memberService) {
        this.memberService = memberService;
    }


    public Long createMember(String email){
        return memberService.SignUp(MemberDto.Request.builder() // enable 항상 true
                .email(email)
                .pwd("12345")
                .pwdChk("12345")
                .city("서울시")
                .zipcode("광진구")
                .street("중곡동")
                .role(Role.GUEST)
                .enabled(true)
                .build());
    }
    public void assertUser(Member member, String email){
        assertNotNull(member.getId());
        assertEquals(email, member.getEmail());
        assertEquals("12345",member.getLow_pwd());
        assertNotNull(member.getAddress());
        assertEquals(member.getAddress().getCity(),"서울시");
        assertTrue(member.isEnabled());
    }


    //권한 체크
//    public void assertUser(Member member, String email,  String... authorities){
//        assertUser(member, email);
//        assertTrue(member.getRole());
//        assertTrue(member.getRole().containsAll(Stream.of(authorities).map(auth->new Authority(auth))));
//    }

}
