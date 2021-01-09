package com.foodPro.demo.order.repository;

import com.foodPro.demo.order.domain.OrderStatus;
import com.foodPro.demo.order.domain.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    /**
     * 주문검색 쿼리 -> QueryDSl
     * @param status
     * @param email
     * @param pageable
     * @return
     */
    @Query(value = "SELECT o FROM Order o join o.member m where o.orderStatus =:status and m.email like :email")
    Page<Orders> OrderSearch(OrderStatus status, String email, Pageable pageable);

}
