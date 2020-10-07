package com.foodPro.demo.order.service;

import com.foodPro.demo.delivery.Delivery;
import com.foodPro.demo.food.domain.Item;
import com.foodPro.demo.food.repository.ItemRepository;
import com.foodPro.demo.member.domain.Member;
import com.foodPro.demo.member.repository.MemberRepository;
import com.foodPro.demo.order.domain.Order;
import com.foodPro.demo.order.domain.OrderItem;
import com.foodPro.demo.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * FUNCTION :: 상품 주문
     * @param itemId :: 아이템 정보
     * @param count :: 수량
     * @return
     */
    public Long save(Long itemId, int count){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(()->
                new IllegalStateException("로그아웃 상태에서 주문시도"));
        Item item = itemRepository.findById(itemId).orElseThrow(()->new IllegalStateException("없는 아이템"));

        // LINE :: 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);
        return order.getId();
    }

}
