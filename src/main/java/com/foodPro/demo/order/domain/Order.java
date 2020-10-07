package com.foodPro.demo.order.domain;

import com.foodPro.demo.member.domain.Member;
import com.foodPro.demo.delivery.Delivery;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Table(name="orders")
@Getter
public class Order {
    @Id @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // LINE :: 주문한 사용자

    @OneToMany(mappedBy = "order" ,cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList = new ArrayList<>(); // LINE :: 주문한 음식

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery; //LINE :: 배송정보

    private LocalDateTime orderDate; //LINE :: 주문시간 < 자바 8 부터 hibernate가 @Data 를 지원해준다.

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; //LINE :: 주문 상태 ORDER , CANCLE

    /**
     * FUNCTION :: 연관관계 메소드
     * @param member
     */
    public void setMember(Member member){
        this.member = member;
        member.getOrderList().add(this);
    }

    public void addOrderFood(OrderItem orderItem){
        orderItemList.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery =delivery;
        delivery.setOrder(this);
    }

}
