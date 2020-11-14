package com.foodPro.demo.DDDEntityTest;

import com.foodPro.demo.config.common.Address;
import com.foodPro.demo.delivery.Delivery;
import com.foodPro.demo.delivery.DeliveryStatus;
import com.foodPro.demo.item.domain.Item;
import com.foodPro.demo.item.dto.ItemDto;
import com.foodPro.demo.member.domain.Member;
import com.foodPro.demo.member.dto.MemberDto;
import com.foodPro.demo.order.domain.Order;
import com.foodPro.demo.order.domain.OrderItem;
import com.foodPro.demo.order.domain.OrderStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.springframework.test.util.AssertionErrors.assertEquals;

//import  org.springframework.test.util.AssertionErrors.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class OrderEntityTest {


    @PersistenceContext
    EntityManager em;



    @Before
    public void common() {

        //given
         String pwd = "123451111111111111111111111";
         String email = "yusa3@naver.com";
         String city = "서울";
         String zipcode = "330";
         String street = "용마산로";
         String bookname = "나미야잡화점";
         int price = 10000;
         int stock = 22;
         String author = "히가시노게이고";
        
        Address address = new Address(city, zipcode, street);

        //when
        em.persist(MemberDto.Request.builder()
                .pwd(pwd)
                .email(email)
                .zipcode(zipcode)
                .city(city)
                .street(street)
                .build().toEntity());
        em.persist(ItemDto.Request.builder()
                .author(author)
                .price(price).name(bookname).gubun("Book").stockQuantity(stock).build().Book_toEntity());
        em.flush();
    }

    /**
     * 상품주문 -- 성공
     * @throws Exception
     */
    @Test
    public void 상품주문() throws Exception{
        //given
        int count = 2; // 수량
        // 배송정보 생성
        Member member = em.find(Member.class, 1L);
        Item item = em.find(Item.class, 1L);
        Delivery delivery = new Delivery();
        // 배송주소
        delivery.setAddress(member.getAddress());
        delivery.setDeliveryStatus(DeliveryStatus.READY);
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);


        //when
        Order order = Order.createOrder(member, delivery, orderItem);
        em.persist(order);

        //then
        Order orderTest = em.find(Order.class, 1L);
        assertEquals("상품 주문시 주문 상태는 ORDER이여야 한다.", OrderStatus.ORDER, orderTest.getOrderStatus());
        assertEquals("주문한 상품 종류수는 2 개 여야 한다.", 1, orderTest.getOrderItemList().size());
        assertEquals("주문 가격은 가격 * 수량이다.", 10000 * count, orderTest.getTotalPrice());
//        assertEquals("주문 수량만큼 재고가 줄어야된다.",6, item.getStockQuantity());
    }

}
