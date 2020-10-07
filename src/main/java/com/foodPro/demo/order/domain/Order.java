package com.foodPro.demo.order.domain;

import com.foodPro.demo.config.common.BaseTimeEntity;
import com.foodPro.demo.delivery.Delivery;
import com.foodPro.demo.delivery.DeliveryStatus;
import com.foodPro.demo.member.domain.Member;
import lombok.Getter;
import org.apache.catalina.valves.HealthCheckValve;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "orders")
@Getter
public class Order extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // LINE :: 주문한 사용자

    @OneToMany(mappedBy = "order", cascade = ALL)
    private List<OrderItem> orderItemList = new ArrayList<>(); // LINE :: 주문한 음식

    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery; //LINE :: 배송정보

    private LocalDateTime orderDate; //LINE :: 주문시간 < 자바 8 부터 hibernate가 @Data 를 지원해준다.

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; //LINE :: 주문 상태 ORDER , CANCLE


    // 연관관계 메소드
    public void setMember(Member member) {
        this.member = member;
        member.getOrderList().add(this);
    }

    public void addOrderFood(OrderItem orderItem) {
        orderItemList.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    // 주문상태 설정
    public void setOrderStatus(OrderStatus status){
            this.orderStatus = status;
    }

    // 생성 시점부터 주문 생성
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member); // LINE :: 주문한 회원
        order.setDelivery(delivery); // LINE :: 배송 정보
        for (OrderItem orderItem: orderItems){
            order.addOrderFood(orderItem); // LINE :: 주문상품
        }
        order.setOrderStatus(OrderStatus.ORDER); // LINE :: 주문
        return order;
    }

    // 비지니스 로직 :: 주문 취소
    public void cancle(){
        if(delivery.getDeliveryStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("사용자가 배송 완료된 상품을 취소 한다.");
        }

        setOrderStatus(OrderStatus.CANCLE);

        for (OrderItem orderItem : orderItemList) {
            orderItem.cancle();
        }
    }

    // 비지니스로직 :: 주문 가격 조회
    public int getTotalPrice(){
        int totalPrice = 0;
        for (OrderItem orderItem : orderItemList) {
            totalPrice +=orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
