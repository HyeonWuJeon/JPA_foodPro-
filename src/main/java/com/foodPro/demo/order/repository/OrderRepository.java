package com.foodPro.demo.order.repository;

import com.foodPro.demo.order.domain.Order;
import com.foodPro.demo.order.domain.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT o FROM Order o join o.member m where o.orderStatus =:status and m.email like :email")
    List<Order> OrderSearch(OrderStatus status, String email);
}
