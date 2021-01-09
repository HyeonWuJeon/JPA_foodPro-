package com.foodPro.demo.order;

import com.foodPro.demo.config.exception.NotEnoughStockException;
import com.foodPro.demo.delivery.Delivery;
import com.foodPro.demo.delivery.DeliveryStatus;
import com.foodPro.demo.item.domain.Item;
import com.foodPro.demo.item.dto.ItemDto;
import com.foodPro.demo.member.domain.Member;
import com.foodPro.demo.member.dto.MemberDto;
import com.foodPro.demo.order.domain.OrderItem;
import com.foodPro.demo.order.domain.OrderStatus;
import com.foodPro.demo.order.domain.Orders;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest // 트랜잭션 포함, 인메모리지원
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderItemDBTest {

    static MemberDto.Request members = new MemberDto.Request();
    static ItemDto.Request items = new ItemDto.Request();
    static Delivery delivery = new Delivery();

    @PersistenceContext
    EntityManager em;


    @Test
    @Order(1)
    @DisplayName("1. 회원 생성") @Rollback(false)
    void member() {

        //given
        String pwd = "12345";
        String email = "yusa3@naver.com";
        String city = "서울";
        String zipcode = "330";
        String street = "용마산로";
        members.setAddress(city,street, zipcode);
        members.setPwd(pwd);
        members.setEmail(email);
        //when
        em.persist(members.toEntity());
        Member member = em.find(Member.class,1L);

        //then
        assertNotNull(member);
        assertEquals(member.getEmail(),email,()->"이메일이 일치하지 않습니다.");
        assertEquals(member.getAddress().getCity(),city,()->"도시 주소가 일치하지 않습니다.");
        assertEquals(member.getPwd(),pwd,()->"패스워드가 일치하지 않습니다.");
    }


    @Test
    @Order(2)
    @DisplayName("2. 아이템 생성")
    void item() {

        //given
        String bookname = "나미야잡화점";
        int price = 10000;
        int stock = 10;
        String author = "히가시노게이고";
        items.setAuthor(author);
        items.setPrice(price);
        items.setStockQuantity(stock);
        items.setName(bookname);
        //when
        em.persist(items.Book_toEntity());
        Item item = em.find(Item.class, 1L);

        //then
        assertNotNull(item);
        assertEquals(item.getName(), bookname, () -> " 책 이름이 일치하지 않습니다.");
        assertEquals(item.getPrice(), price, () -> "가격이 일치하지 않습니다");
        assertEquals(item.getStockQuantity(),stock,() ->"수량이 일치하지 않습니다.");
    }

    @Test
    @Order(3)
    @DisplayName("3. 배송정보 생성")
    void Delivery() throws Exception {

        // given
        delivery.setAddress(members.getAddress());
        delivery.setDeliveryStatus(DeliveryStatus.READY);

        //when
//        em.persist(delivery); cascade Type All

        // then
        assertEquals(delivery.getAddress(), members.getAddress(), () -> "주소가 일치하지 않습니다.");
        assertEquals(delivery.getDeliveryStatus(),DeliveryStatus.READY,() ->"배송상태가 일치하지 않습니다.");

    }

    /**
     * 상품주문 -- 성공
     * @throws Exception
//     */
    @Test
    @Order(4)
    @DisplayName("4. 상품 주문")
    public void orderItem() throws Exception{
        //given
        int count = 2; // 수량
        OrderItem orderItem = OrderItem.createOrderItem(items.Book_toEntity(), items.getPrice(), count);

        //when
        Orders order = Orders.createOrder(members.toEntity(), delivery, orderItem);
        em.persist(order);

        //then
        Orders orderTest = em.find(Orders.class, 1L);
        assertEquals(orderTest.getOrderStatus(), OrderStatus.ORDER,()->"주문상태는 Order 이여야 한다.");
        assertEquals(count, orderTest.getOrderItemList().get(0).getCount(), ()->"주문한 수량은 count 개 이여야 한다");
        assertEquals(items.getPrice() * count, orderTest.getTotalPrice(), ()->"주문 가격은 가격 * 수량이다.");
        assertEquals(8, orderTest.getOrderItemList().get(0).getItem().getStockQuantity(),()->"주문 수량만큼 재고가 줄어야된다.");
    }

    /**
     * 상품주문 예외처리
     */
    @Test
    @Order(5)
    @DisplayName("5. 수량초과 주문")
    void orderItemException() throws Exception{
        //given
        int count = 100;

        //when
        Exception exception = assertThrows(NotEnoughStockException.class, ()-> OrderItem.createOrderItem(items.Book_toEntity(), items.getPrice(), count));

        //then
        assertEquals("need more stock", exception.getMessage());
    }


}
