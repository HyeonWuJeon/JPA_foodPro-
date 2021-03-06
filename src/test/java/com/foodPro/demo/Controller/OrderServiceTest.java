package com.foodPro.demo.Controller;

import com.foodPro.demo.config.common.Address;
import com.foodPro.demo.config.exception.NotEnoughStockException;
import com.foodPro.demo.item.dto.ItemDto;
import com.foodPro.demo.item.service.ItemService;
import com.foodPro.demo.member.dto.MemberDto;
import com.foodPro.demo.member.service.MemberServiceImpl;
import com.foodPro.demo.order.domain.Order;
import com.foodPro.demo.order.domain.OrderStatus;
import com.foodPro.demo.order.dto.OrderDto;
import com.foodPro.demo.order.repository.OrderRepository;
import com.foodPro.demo.order.service.OrderService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;




@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
    static String pwd = "12345";
    static String email = "yusa3@naver.com";
    static String city = "서울";
    static String zipcode = "330";
    static String street = "용마산로";

    static String bookname = "나미야잡화점";
    static int price = 10000;
    static int stock = 22;
    static String author = "히가시노게이고";

    @BeforeEach
    void common() {
 // 회원 가입
        memberServiceImpl.
                SignUp(MemberDto.Request.builder()
                .pwd(pwd)
                .email(email)
                .pwdChk(pwd)
                .zipcode(zipcode)
                .city(city)
                .street(street)
                .build());
// 아이템 저장
        ItemDto.Request request2 = new ItemDto.Request();
        itemService.saveItem(ItemDto.Request.builder()
                .price(price)
                .name(bookname)
                .author(author)
                .gubun("Book")
                .stockQuantity(stock).build());
    }


//    @Test
//    public void 조회() throws Exception{
//        Pageable pageable = new Pageable() {
//            @Override
//            public int getPageNumber() {
//                return 0;
//            }
//
//            @Override
//            public int getPageSize() {
//                return 10;
//            }
//
//            @Override
//            public long getOffset() {
//                return 0;
//            }
//
//            @Override
//            public Sort getSort() {
//                return Sort.unsorted();
//            }
//
//            @Override
//            public Pageable next() {
//                return null;
//            }
//
//            @Override
//            public Pageable previousOrFirst() {
//                return null;
//            }
//
//            @Override
//            public Pageable first() {
//                return null;
//            }
//
//            @Override
//            public boolean hasPrevious() {
//                return false;
//            }
//        };
//
//        Page<OrderDto.Response> orders = orderService.findAllDesc(OrderStatus.ORDER,"yusa3@naver.com", pageable);
//
//        System.out.println("orders.toString() = " + orders.getTotalElements());
//        System.out.println("orders.getTotalPages() = " + orders.getTotalPages());
//        System.out.println("orders.getContent() = " + orders.getContent().toString());
//        System.out.println("orders.getPageable() = " + orders.getPageable());
//    }


    @DisplayName("1. 상품주문 초과 오류")
    @Test
    public void OrderException() throws Exception{
        //given
        MemberDto.Response member = memberServiceImpl.findById(1L);
        ItemDto.Response item = itemService.findById(1L);
        int orderCount = 100;
        //when
        Exception exception = assertThrows(NotEnoughStockException.class, ()->orderService.save(member.getId(),item.getId(), orderCount));
        assertEquals("need more stock",exception.getMessage(),()->"need more stock 에러가 나와야 한다.");
    }



    @Test
    public void 주문취소() {
        //given
        MemberDto.Response member = memberServiceImpl.findById(1L);
        System.out.println("member.getEmail() = " + member.getEmail());
        ItemDto.Response item = itemService.findById(33L);

        int orderCount= 2;

        Long orderId = orderService.save(member.getId(),item.getId(),orderCount);

        //when
        orderService.cancleOrder(orderId);

        //then
        Order getOrder = orderRepository.findById(orderId).get();

        assertEquals( OrderStatus.CANCLE, getOrder.getOrderStatus());
        assertEquals(2, item.getStockQuantity());

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
