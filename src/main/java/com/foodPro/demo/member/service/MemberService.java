package com.foodPro.demo.member.service;

import com.foodPro.demo.config.common.Address;
import com.foodPro.demo.config.common.ApplicationService;
import com.foodPro.demo.config.exception.MemberDuplicationException;
import com.foodPro.demo.config.exception.UserNotFoundException;
import com.foodPro.demo.config.filter.JwtTokenProvider;
import com.foodPro.demo.member.domain.Member;
import com.foodPro.demo.member.dto.MemberDto;
import com.foodPro.demo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@Service("memberService")
@RequiredArgsConstructor
public class MemberService extends ApplicationService implements UserDetailsService {

    private final MemberRepository memberRepository;
    /**
     * FUNCTION :: 회원가입
     * @param form
     * @return
     */
    public String SignUp(MemberDto.Request form) {
        HashMap<String, Object> rtnMap = returnMap();
        //LINE :: 패스워드 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //LINE :: 저장 + 유효성 검사
        memberRepository.save(new MemberDto.Request().builder()
                .name(form.getName())
                .address(new Address(form.getCity(), form.getZipcode(), form.getStreet()))
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
     * @param userEmail
     * @return
     */
    @Transactional(readOnly = true)
    public void validateDuplicateMember(String userEmail) {
            if (memberRepository.findByEmail(userEmail).isPresent()) {
                throw new MemberDuplicationException("회원 중복 오류");
        }
    }

    /**
     * FUNCTION :: 인증절차
     * @param userEmail
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public MemberDto.Response loadUserByUsername(String userEmail) throws UsernameNotFoundException{
        return  memberRepository.findByEmail(userEmail).map(u ->
                new MemberDto.Response(u, Collections.singleton(new SimpleGrantedAuthority(u.getRole().getValue())))).orElseThrow(()
                -> new UserNotFoundException(userEmail));
    }

    /**
     * FUNCTION :: 회원 전체 조회
     * @return
     */
    @Transactional(readOnly = true)
    public List<MemberDto.Response> findAllDesc() {
        return memberRepository.findAllDesc().stream()
                .map(MemberDto.Response::new)
                .collect(Collectors.toList());
    }

    /**
     * FUNCTION :: 회원 개별 조회
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public MemberDto.Response findById(Long id) {
        Member entity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + id));

        return new MemberDto.Response(entity);
    }

    /**
     * FUNCTION :: 회원 수정
     * @param id
     * @param requestDto
     * @return
     */
    @Transactional
    public Long update(Long id, MemberDto.Request requestDto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + id));
        member.update(requestDto.getCity(), requestDto.getStreet(), requestDto.getZipcode(), requestDto.getPhone());

        return id;
    }
}
