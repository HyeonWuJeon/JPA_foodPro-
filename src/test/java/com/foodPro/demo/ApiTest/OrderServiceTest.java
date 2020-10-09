package com.foodPro.demo.ApiTest;

import com.foodPro.demo.config.exception.NotEnoughStockException;
import com.foodPro.demo.item.domain.item.Book;
import com.foodPro.demo.item.dto.ItemDto;
import com.foodPro.demo.item.repository.ItemRepository;
import com.foodPro.demo.item.service.ItemService;
import com.foodPro.demo.member.domain.Member;
import com.foodPro.demo.member.dto.MemberDto;
import com.foodPro.demo.member.service.MemberService;
import com.foodPro.demo.order.domain.Order;
import com.foodPro.demo.order.domain.OrderStatus;
import com.foodPro.demo.order.repository.OrderRepository;
import com.foodPro.demo.order.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired OrderService orderService;
    @Autowired MemberService memberService;
    @Autowired OrderRepository orderRepository;
    @Autowired ItemService itemService;

    @Test
    public void 상품주문() throws Exception{
        //given
        MemberDto.Response member = memberService.findById(1L);
        ItemDto.Response item = itemService.findById(33L);

        int orderCount = 2;
        //when
        Long orderId = orderService.save(member.getId(),item.getId(), orderCount);
        //then
        Order getOrder = orderRepository.findById(1L).get();
        assertEquals("상품 주문시 주문 상태는 ORDER이여야 한다.", OrderStatus.ORDER, getOrder.getOrderStatus());
        assertEquals("주문한 상품 종류수는 1 개 여야 한다.", 1, getOrder.getOrderItemList().size());
        assertEquals("주문 가격은 가격 * 수량이다.", 10000 * orderCount, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야된다.",6, item.getStockQuantity());
    }


    @Test(expected = NotEnoughStockException.class)
    public void 상품주문초과() throws Exception{
        //given
        MemberDto.Response member = memberService.findById(1L);
        ItemDto.Response item = itemService.findById(33L);
        int orderCount = 11;
        //when
        orderService.save(member.getId(),item.getId(), orderCount);
        //then
        fail("재고수량 부족 예외");
    }

    @Test
    public void 주문취소() {
        //given
        MemberDto.Response member = memberService.findById(1L);
        ItemDto.Response item = itemService.findById(33L);

        int orderCount= 2;

        Long orderId = orderService.save(member.getId(),item.getId(),orderCount);

        //when
        orderService.cancleOrder(orderId);

        //then
        Order getOrder = orderRepository.findById(orderId).get();

        assertEquals("주문 취소상태 는 CANCLE 이다.", OrderStatus.CANCLE, getOrder.getOrderStatus());
        assertEquals("주문 재고가 증가해야한다.", 2, item.getStockQuantity());

    }
}
