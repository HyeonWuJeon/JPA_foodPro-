package com.foodPro.demo.ApiTest;


import com.foodPro.demo.member.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class MemberApiTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean @Qualifier("memberService")
    MemberService memberService;



    @Test
    public void GlobalException() {
        //given
        Long id = 1L;
        given(memberService.findById(id))
                .willReturn(null);

        //when
        webTestClient.get().uri("/member/settings/1").exchange()
                .expectStatus().is4xxClientError();
    }

}
