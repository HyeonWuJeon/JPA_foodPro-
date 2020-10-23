package com.foodPro.demo.order.repository;

import com.foodPro.demo.member.domain.Member;
import com.foodPro.demo.order.domain.Order;
import com.foodPro.demo.order.domain.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT o FROM Order o join o.member m where o.orderStatus =:status and m.email like :email")
    Page<Order> OrderSearch(OrderStatus status, String email, Pageable pageable);

//    @Query(value = "SELECT m FROM Member m ORDER BY m.id DESC")
//    Page<Member> findAllDesc(Pageable pageable);
}
