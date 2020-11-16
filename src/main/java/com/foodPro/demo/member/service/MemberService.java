package com.foodPro.demo.member.service;

import com.foodPro.demo.member.dto.MemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberService {
    Long SignUp(MemberDto.Request form);
    void validateDuplicateMember(String userEmail);
    void passwordSameChk(String pwd, String pwdChk);
    Page<MemberDto.Response> findAllDesc(Pageable pageable);
    MemberDto.Response findById(Long id);
    Long update(Long id, MemberDto.Request requestDto);
    void delete(Long id);


}
