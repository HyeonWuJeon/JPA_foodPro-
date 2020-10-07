package com.foodPro.demo.order.domain;

import com.foodPro.demo.food.domain.Item;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_food_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "food_id")
    private Item item; //LINE :: 음식 외래키

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order; //LINE :: 주문 외래키

    private int orderPrice; // LINE :: 주문가격
    private int count; // LINE :: 주문수량

    public void setOrder(Order order) {
        this.order = order;
    }
}
