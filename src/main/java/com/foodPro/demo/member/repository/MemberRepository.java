package com.foodPro.demo.member.repository;

import com.foodPro.demo.member.domain.Member;
import com.foodPro.demo.order.domain.Order;
import com.foodPro.demo.order.domain.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
    //LINE :: 회원 이메일 조회
    Optional<Member> findByEmail(String email);

    @Query(value = "SELECT m FROM Member m ORDER BY m.id DESC")
    Page<Member> findAllDesc(Pageable pageable);


}
