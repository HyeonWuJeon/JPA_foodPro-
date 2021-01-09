package com.foodPro.demo.order.domain;

import com.foodPro.demo.config.common.BaseTimeEntity;
import com.foodPro.demo.item.domain.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = PROTECTED)
public class OrderItem extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "order_food_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item; //LINE :: 음식 외래키

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Orders order; //LINE :: 주문 외래키

    private int orderPrice; // LINE :: 주문가격
    private int count; // LINE :: 주문수량

    public void setOrder(Orders order) {
        this.order = order;
    }

    /**
     * 외부에서 인스턴스를 생성하여 변수를 못끌어다 쓰게금 protected 생성자로 막는다.
     * 외부 클래스의 함수에서 변수가 함부로 남용(setter) 되어 쓸 경우 유지보수 하기 힘들어진다.
     * OrderItem orderItem = new OrderItem() <- Compile Error
     * @NoArgs(access = protected)로 대체할 수 있다.
     */

    // 비지니스로직 :: 주문
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item); // LINE :: 구매한 아이템 정보
        orderItem.setOrderPrice(orderPrice); // LINE :: 구매한 아이템 가격
        orderItem.setCount(count); //LINE :: 구매한 아이템 수량

        item.removeStock(count); // LINE :: 구매한 수량만큼 아이템 제거
        return orderItem;
    }

    /**
     * 변경내역 감지
     */
    // 비지니스 로직 :: 취소한 만큼 재고 수량 원복
    public void cancle() {
        getItem().addStock(count);
    }

    // 비지니스 로직 :: 총 주문 금액
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
