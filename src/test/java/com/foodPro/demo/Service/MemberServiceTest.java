package com.foodPro.demo.Service;


import com.foodPro.demo.config.common.Address;
import com.foodPro.demo.config.exception.MemberDuplicationException;
import com.foodPro.demo.config.exception.PasswordMissmatchException;
import com.foodPro.demo.config.exception.UserNotFoundException;
import com.foodPro.demo.config.security.Role;
import com.foodPro.demo.member.domain.Member;
import com.foodPro.demo.member.dto.MemberDto;
import com.foodPro.demo.member.repository.MemberRepository;
import com.foodPro.demo.member.service.MemberServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    private MemberServiceImpl memberServiceImpl;

    @MockBean
    private MemberRepository memberRepository;

    //given
    static String pwd = "12345";
    static String email = "yusa10@naver.com";
    static String city = "서울";
    static String zipcode = "330";
    static String street = "용마산로";

//    @Before
//    public void Before(){
//        Address address = new Address(city, zipcode, street);
//        MemberDto.Request request = new MemberDto.Request();
//        memberServiceImpl.SignUp(request.builder()
//                .pwd(pwd)
//                .pwdChk(pwd)
//                .low_pwd(pwd)
//                .email(email)
//                .zipcode(address.getZipcode())
//                .city(address.getCity())
//                .street(address.getStreet())
//                .role(Role.ADMIN)
//                .build());
//    }
    /**
     * 비지니스로직 UNIT TEST
     * 1. 조회
     */
    @Test
    public void 조회(){

        //when
        Page<MemberDto.Response> List = memberServiceImpl.findAllDesc(Pageable.unpaged());
        System.out.println("List = " + List);

        for (MemberDto.Response member : List){
            System.out.println("member.toString() = " + member.toString());
        }
    }

    /**
     * 2. 저장
     */
    @Test
    public void 저장(){
        //given
        Address address = new Address(city, zipcode, street);

        //when
        Long id = memberServiceImpl.SignUp(MemberDto.Request.builder()
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

    /**
     * 수정
     */
    @Test
    @Rollback(false)
    public void 수정(){
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

    /**
     * 삭제 -- 성공
     */
    @Test(expected = NullPointerException.class)

    public void 삭제() {
        memberServiceImpl.delete(1L);
        //then
        Member memberEntity = memberRepository.findById(1L).orElseThrow(()-> new NullPointerException("null"));
    }

    /**
     * 예외처리 테스트
     */
    // 이메일 중복 검사 -- 성공
    @Test(expected = MemberDuplicationException.class)
    public void 이메일_중복검사(){

        //given
        String email = "yusa3@naver.com";
        //when
        memberServiceImpl.validateDuplicateMember(email);
    }

    @Test(expected = PasswordMissmatchException.class)
    public void 패스워드매칭() {
        //given
        String password = "12345";

        //when
        memberServiceImpl.passwordSameChk(password,"123");
    }

    /**
     * NPE
     */
    //https://jojoldu.tistory.com/226 참고
    //https://stackoverflow.com/questions/38881233/java-test-with-expected-exception-fails-with-assertion-error assertError 해결
    // 회원 조회 NPE -- 성공
    @Test(expected = UserNotFoundException.class)
    public void 조회NPE() throws Exception {
        //given
        Long id = 1L;
        given(memberRepository.findById(id))
                .willReturn(Optional.empty());

        //when
        memberServiceImpl.findById(id);

    }


    // 회원 수정 NPE -- 성공
    @Test(expected = UserNotFoundException.class)
    public void 수정NPE() throws Exception {

        //given
        Long id = 1L;
        given(memberRepository.findById(id))
                .willReturn(Optional.empty());

        //when
        memberServiceImpl.update(id, MemberDto.Request.builder().email(email).build());

    }

    /**
     * 삭제 NPE -- 성공
     * @throws Exception
     */
    @Test(expected = UserNotFoundException.class)
    public void 삭제NPE() throws Exception {

        //given
        Long id = 1L;
        given(memberRepository.findById(id))
                .willReturn(Optional.empty());

        //when
        memberServiceImpl.delete(id);
    }

}
