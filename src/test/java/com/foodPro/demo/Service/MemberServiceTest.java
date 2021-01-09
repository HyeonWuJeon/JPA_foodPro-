package com.foodPro.demo.Service;


import com.foodPro.demo.config.common.Address;
import com.foodPro.demo.config.exception.MemberDuplicationException;
import com.foodPro.demo.config.exception.PasswordMissmatchException;
import com.foodPro.demo.config.exception.UserNotFoundException;
import com.foodPro.demo.config.security.Role;
import com.foodPro.demo.member.domain.Member;
import com.foodPro.demo.member.dto.MemberDto;
import com.foodPro.demo.member.repository.MemberRepository;
import com.foodPro.demo.member.service.MemberService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;


@Nested
@DisplayName("회원 CRUD 테스트")
@ActiveProfiles("test")
@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemberServiceTest {

    @Autowired
    private MemberService memberServiceImpl;

    @Autowired
    private MemberRepository memberRepository;

    //given
    static String pwd = "12345";
    static String email = "yusa77@naver.com";
    static String city = "서울";
    static String zipcode = "330";
    static String street = "용마산로";


    /**
     * 1. 저장
     */
    @Test
    @Order(1)
    void save() {
        //given
        Address address = new Address(city, zipcode, street);
        //when
        Long id = memberServiceImpl.SignUp(MemberDto.Request.builder()
                .pwd(pwd)
                .pwdChk(pwd)
                .email(email)
                .zipcode(address.getZipcode())
                .city(address.getCity())
                .street(address.getStreet())
                .role(Role.ADMIN)
                .build());


        //then
        Member member = memberRepository.findById(id).get();
        assertEquals(member.getEmail(), email, () -> "email 값이 불일치합니다.");
        assertEquals(member.getAddress().getCity(), city);

    }

    /**
     * 2. 조회
     */
    @Test
    @Order(2)
    void find() {

        //when
        Page<MemberDto.Response> List = memberServiceImpl.findAllDesc(Pageable.unpaged());
        System.out.println("List = " + List);

        for (MemberDto.Response member : List) {
            System.out.println("member.toString() = " + member.toString());
        }
        assertEquals(List.getTotalElements(), 1);
        assertEquals(List.getContent().get(0).getEmail(), email);
    }

    /**
     * 수정
     */
    @Test
    @Order(3)
    void modify() {
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
        assertEquals(member.getAddress().getCity(), "경기도");

    }

    /**
     * 삭제 -- 성공
     */
    @Test
    @Order(4)
    public void delete() {
        memberServiceImpl.delete(1L);


        Exception exception = assertThrows(UserNotFoundException.class, () -> memberServiceImpl.findById(1L));
        //then
        assertEquals("UserNotFoundException :: FunctionName == > findById", exception.getMessage());

    }


    /**
     * 예외처리 테스트
     */
    @Nested
    @SpringBootTest
    @DisplayName("user 예외처리 테스트")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class Exception_Test {


        @Autowired
        private MemberService memberServiceImpl;

        @MockBean
        MemberRepository memberRepository;

        @Test
        @DisplayName("1.이메일 중복검사")
        @Order(1)
        void email() {
            Optional<Member> member = Optional.of(new Member());
            given(memberRepository.findByEmail(email)).willReturn(member);

            //when
            Exception exception = assertThrows(MemberDuplicationException.class, () -> memberServiceImpl.validateDuplicateMember(email));
            //then
            assertEquals("MemberDuplicationException :: FunctionName ==> validateDuplicateMember", exception.getMessage(), () -> "예외처리");
        }

        @Test
        @DisplayName("2. 패스워드 매칭 검사")
        @Order(2)
        void password() {
            //given
            String password = "12345";
            //when
            Exception exception = assertThrows(PasswordMissmatchException.class, () -> memberServiceImpl.passwordSameChk(password, "123"));
            //then
            assertEquals("PasswordMismatchException :: FunctionName ==> passwordSameChk", exception.getMessage(), () -> "예외처리");

        }

        /**
         * NPE
         */
        //https://jojoldu.tistory.com/226 참고
        //https://stackoverflow.com/questions/38881233/java-test-with-expected-exception-fails-with-assertion-error assertError 해결
        // 회원 조회 NPE -- 성공
        @Test
        @DisplayName("3. 회원조회 NPE 검사")
        @Order(3)
        void searchNPE() throws Exception {
            //given
            Long id = 1L;
            given(memberRepository.findById(id))
                    .willReturn(Optional.empty());

            //when
            Exception exception = assertThrows(UserNotFoundException.class, () -> memberServiceImpl.findById(id));
            //then
            assertEquals("UserNotFoundException :: FunctionName == > findById", exception.getMessage(), () -> "예외처리");
        }

        // 회원 수정 NPE -- 성공
        @Test
        @DisplayName("3. 회원수정 NPE 검사")
        @Order(4)
        void modifyNPE() throws Exception {

            //given
            Long id = 1L;
            given(memberRepository.findById(id))
                    .willReturn(Optional.empty());

            //when
            Exception exception = assertThrows(UserNotFoundException.class, () -> memberServiceImpl.update(id, MemberDto.Request.builder().email(email).build()));
            //then
            assertEquals("UserNotFoundException :: FunctionName == > update", exception.getMessage(), () -> "예외처리");

        }

        // 삭제 NPE -- 성공
        @Test
        @DisplayName("4. 회원삭제 NPE 검사")
        @Order(5)
        void deleteNPE() throws Exception {

            //given
            Long id = 1L;
            given(memberRepository.findById(id))
                    .willReturn(Optional.empty());

            //when
            Exception exception = assertThrows(UserNotFoundException.class, () -> memberServiceImpl.delete(id));
            //then
            assertEquals("UserNotFoundException :: FunctionName == > delete", exception.getMessage(), () -> "예외처리");
        }

    }
}


