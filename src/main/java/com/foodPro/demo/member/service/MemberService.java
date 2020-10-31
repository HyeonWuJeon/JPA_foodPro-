package com.foodPro.demo.member.service;

import com.foodPro.demo.member.dto.MemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberService {
    String SignUp(MemberDto.Request form);
    void validateDuplicateMember(String userEmail);
    void passwordSameChk(String pwd, String pwdChk);
    Page<MemberDto.Response> findAllDesc(Pageable pageable, int age);
    MemberDto.Response findById(Long id);
    Long update(Long id, MemberDto.Request requestDto);



}
