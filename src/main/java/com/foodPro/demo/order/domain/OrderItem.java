package com.foodPro.demo.order.domain;

import com.foodPro.demo.config.common.BaseTimeEntity;
import com.foodPro.demo.food.domain.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class OrderItem extends BaseTimeEntity {

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

    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item); // LINE :: 구매한 아이템 정보
        orderItem.setOrderPrice(orderPrice); // LINE :: 구매한 아이템 가격
        orderItem.setCount(count); //LINE :: 구매한 아이템 수량

        item.removeStock(count); // LINE :: 구매한 수량만큼 아이템 제거
        return orderItem;
    }

    // 비지니스 로직 :: 취소한 만큼 재고 수량 원복
    public void cancle() {
        getItem().addStock(count);
    }

    // 비지니스 로직 :: 총 주문 금액
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
