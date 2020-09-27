package com.foodPro.demo.member.service;

import com.foodPro.demo.config.common.Address;
import com.foodPro.demo.config.common.ApplicationService;
import com.foodPro.demo.config.exception.MemberDuplicationException;
import com.foodPro.demo.member.domain.Member;
import com.foodPro.demo.member.dto.MemberDto;
import com.foodPro.demo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MemberService extends ApplicationService {

    private final MemberRepository memberRepository;

    /**
     * FUNCTION :: 회원가입
     * @param form
     * @return
     */
    public String SignUp(MemberDto.Request form) {
        HashMap<String, Object> rtnMap = returnMap();
        //LINE :: 이메일 중복검사
        validateDuplicateMember(form);
        //LINE :: 패스워드 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //LINE :: 저장 + 유효성 검사
        System.out.println("form.getPwd() 패스워드임다= " + form.getPwd());
        System.out.println("form.getName() 이름임돠= " + form.getName());
        memberRepository.save(new MemberDto.Request().builder()
                .name(form.getName())
                .address(new Address(form.getZipcode(), form.getCity(), form.getStreet()))
                .birth(form.getBirth())
                .email(form.getEmail())
                .pwd(passwordEncoder.encode(form.getPwd()))
                .phone(form.getPhone())
                .build().toEntity());
        rtnMap.put(AJAX_RESULT_TEXT, AJAX_RESULT_SUCCESS); //성공
        return jsonFormatTransfer(rtnMap);
    }

    /**
     * FUNCTION :: 아이디 중복검사
     * @param requestDto
     * @return
     */
    @Transactional(readOnly = true)
    public void validateDuplicateMember(MemberDto.Request requestDto) throws MemberDuplicationException {
        List<Member> findMember = memberRepository.findByEmail(requestDto.getEmail());
        if(!findMember.isEmpty()){
               throw new MemberDuplicationException("회원 중복 오류");
        }
    }
}
