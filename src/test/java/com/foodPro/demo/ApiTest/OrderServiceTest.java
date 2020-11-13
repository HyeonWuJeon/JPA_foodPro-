package com.foodPro.demo.ApiTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodPro.demo.config.common.Address;
import com.foodPro.demo.config.exception.NotEnoughStockException;
import com.foodPro.demo.config.security.Role;
import com.foodPro.demo.item.dto.ItemDto;
import com.foodPro.demo.item.service.ItemService;
import com.foodPro.demo.member.domain.Member;
import com.foodPro.demo.member.dto.MemberDto;
import com.foodPro.demo.member.service.MemberServiceImpl;
import com.foodPro.demo.order.domain.Order;
import com.foodPro.demo.order.domain.OrderStatus;
import com.foodPro.demo.order.dto.OrderDto;
import com.foodPro.demo.order.repository.OrderRepository;
import com.foodPro.demo.order.service.OrderService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class OrderServiceTest {

    @Autowired OrderService orderService;
    @Autowired MemberServiceImpl memberServiceImpl;
    @Autowired OrderRepository orderRepository;
    @Autowired ItemService itemService;

    @LocalServerPort
    private int port;

    @PersistenceContext
    EntityManager em;

    private MockMvc mvc;

    //given
    static String pwd = "123451111111111111111111111";
    static String email = "yusa3@naver.com";
    static String city = "서울";
    static String zipcode = "330";
    static String street = "용마산로";

    static String bookname = "나미야잡화점";
    static int price = 10000;
    static int stock = 22;
    static String author = "히가시노게이고";

//    @Before
//    public void common() {
//
//        //given
//        Address address = new Address(city, zipcode, street);
//
//        memberServiceImpl.
//                SignUp(MemberDto.Request.builder()
//                .pwd(pwd)
//                .email(email)
//                .zipcode(zipcode)
//                .city(city)
//                .street(street)
//                .build());
//
//        ItemDto.Request request2 = new ItemDto.Request();
//        request2.setPrice(price);
//        request2.setName(bookname);
//        request2.setAuthor(author);
//        request2.setGubun("Book");
//        request2.setStockQuantity(stock);
//
//        itemService.saveItem(request2);
//
//    }
    @Test
    public void 상품주문() throws Exception{
        //given
        Member member1 = em.find(Member.class, 1L);
        System.out.println("member1.toString() = " + member1.toString());
        MemberDto.Response member = memberServiceImpl.findById(1L);
        ItemDto.Response item = itemService.findById(1L);

        int orderCount = 2;
        //when
        Long orderId = orderService.save(member.getId(),item.getId(), orderCount);
        //then
        Order getOrder = orderRepository.findById(1L).get();
        assertEquals("상품 주문시 주문 상태는 ORDER이여야 한다.", OrderStatus.ORDER, getOrder.getOrderStatus());
        assertEquals("주문한 상품 종류수는 1 개 여야 한다.", 1, getOrder.getOrderItemList().size());
        assertEquals("주문 가격은 가격 * 수량이다.", 10000 * orderCount, getOrder.getTotalPrice());
//        assertEquals("주문 수량만큼 재고가 줄어야된다.",6, item.getStockQuantity());
    }

    @Test
    public void 조회() throws Exception{

        Pageable pageable = new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 10;
            }

            @Override
            public long getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return Sort.unsorted();
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        };

        Page<OrderDto.Response> orders = orderService.findAllDesc(OrderStatus.ORDER,"yusa3@naver.com", pageable);

        System.out.println("orders.toString() = " + orders.getTotalElements());
        System.out.println("orders.getTotalPages() = " + orders.getTotalPages());
        System.out.println("orders.getContent() = " + orders.getContent().toString());
        System.out.println("orders.getPageable() = " + orders.getPageable());
    }


    @Test(expected = NotEnoughStockException.class)
    public void 상품주문초과() throws Exception{
        //given
        MemberDto.Response member = memberServiceImpl.findById(1L);
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
        MemberDto.Response member = memberServiceImpl.findById(1L);
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


    /**
     * Exception 테스트
     */
    @Test
    public void 주문예외처리() {

            // 회원 없는 상태에서 주문.
        String url = "http://localhost:" + port + "/api/v1/posts";

        //when
//        mvc.perform(post(url)
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(new ObjectMapper().writeValueAsString(1L, 1L, 1L)))
//                .andExpect(status().isOk());


    }
}
