package com.foodPro.demo.ApiTest;


import com.foodPro.demo.config.common.Address;
import com.foodPro.demo.config.exception.MemberDuplicationException;
import com.foodPro.demo.config.security.Role;
import com.foodPro.demo.member.domain.Member;
import com.foodPro.demo.member.dto.MemberDto;
import com.foodPro.demo.member.repository.MemberRepository;
import com.foodPro.demo.member.service.MemberServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Transactional
//@AutoConfigureMockMvc
public class MemberServiceImplTest {

//    @Autowired
    @MockBean
    private MemberServiceImpl memberServiceImpl;
    @MockBean
    private MemberRepository memberRepository;


    @PersistenceContext
    EntityManager em;

    @LocalServerPort
    private int port;

    @Autowired
    WebTestClient webTestClient;


    //given
    static String name = "";
    static String pwd = "12345";
    static String phone = "0109259";
    static String birth = "1995-11-29";
    static String email = "yusa10@naver.com";
    static String city = "서울";
    static String zipcode = "330";
    static String street = "용마산로";

    @Test
    public void 회원조회(){
//        Page<Member> List = memberService.findAllDesc(Pageable.unpaged());
//        System.out.println("List = " + List);
//
//        for (Member member : List){
//            System.out.println("member.toString() = " + member.toString());
//        }
    }

    @Test
    @Rollback(false)
    public void 회원가입(){
        //given
        Address address = new Address(city, zipcode, street);
        MemberDto.Request request = new MemberDto.Request();

        //when
        Long id = memberServiceImpl.SignUp(request.builder()
                .pwd(pwd)
                .pwdChk(pwd)
                .low_pwd(pwd)
                .email(email)
                .zipcode(address.getZipcode())
                .city(address.getCity())
                .street(address.getStreet())
                .role(Role.ADMIN)
                .build());
        //then
        Member member = memberRepository.findById(id).get();
        assertThat(member.getEmail()).isEqualTo(email);
    }


    @Test(expected = MemberDuplicationException.class)
    public void 이메일_중복검사(){

        memberServiceImpl.validateDuplicateMember(email);

    }

    @Test
    @Rollback(false)
    public void 회원수정(){
        //given
        Address address = new Address("경기도", "광명", "00동");
        //when
        memberServiceImpl.update(1L, MemberDto.Request.builder()
                .city(address.getCity())
                .street(address.getStreet())
                .zipcode(address.getZipcode())
                .build());

        //then
        Member member = memberRepository.findById(1L).get();
        assertThat(member.getAddress().getCity()).isEqualTo("경기도");

    }

    //https://jojoldu.tistory.com/226 참고
    //https://stackoverflow.com/questions/38881233/java-test-with-expected-exception-fails-with-assertion-error assertError 해결
    @Test(expected = IllegalArgumentException.class)
    public void 조회예외처리테스트() throws Exception {
        //given
        Long id = 1L;
        given(memberRepository.findById(id))
                .willReturn(Optional.empty());

        //when
        memberServiceImpl.findById(id);

    }

    @Test
    public void GlobalException() {
        //given
        Long id = 1L;
        given(memberServiceImpl.findById(id))
                .willReturn(null);

        //when
        webTestClient.get().uri("/member/settings/1").exchange()
                .expectStatus().is4xxClientError();
    }
}
