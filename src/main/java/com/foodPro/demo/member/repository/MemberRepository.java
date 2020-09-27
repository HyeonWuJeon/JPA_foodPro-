package com.foodPro.demo.member.repository;

import com.foodPro.demo.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByEmail(String Email);
}
