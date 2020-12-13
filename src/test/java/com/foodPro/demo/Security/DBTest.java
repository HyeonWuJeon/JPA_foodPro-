package com.foodPro.demo.Security;


import com.foodPro.demo.Service.UserTestHelper;
import com.foodPro.demo.member.domain.Member;
import com.foodPro.demo.member.dto.MemberDto;
import com.foodPro.demo.member.repository.MemberRepository;
import com.foodPro.demo.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest
public class DBTest {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    private UserTestHelper userTestHelper;

    @BeforeEach
    void setUp() {
        this.userTestHelper = new UserTestHelper(memberService);
        this.memberRepository.deleteAll();
    }

    @DisplayName("1.사용자를 생성한다.")
    @Test
    void test_1() {

        userTestHelper.createMember("test@gmail.com");

        List<Member> memberList = this.memberRepository.findAll();

        assertEquals(1, memberList.size());

        userTestHelper.assertUser(memberList.get(0), "test@gmail.com");
    }

//    @DisplayName("2. authority를 부여한다")
}
