package com.foodPro.demo.order.controller;

import com.foodPro.demo.config.common.pagging.PageWrapper;
import com.foodPro.demo.item.domain.Item;
import com.foodPro.demo.item.dto.ItemDto;
import com.foodPro.demo.item.service.ItemService;
import com.foodPro.demo.member.domain.Member;
import com.foodPro.demo.member.dto.MemberDto;
import com.foodPro.demo.member.service.MemberService;
import com.foodPro.demo.order.domain.Order;
import com.foodPro.demo.order.domain.OrderStatus;
import com.foodPro.demo.order.dto.OrderDto;
import com.foodPro.demo.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

//    @GetMapping("/list")
//    public String findAllDesc(OrderStatus status, String email, Model model, Pageable pageable) {
//
//
//        Page<OrderDto.Response> responses = orderService.findAllDesc(status, email, pageable);
//        PageWrapper<OrderDto.Response> page = new PageWrapper<>(responses, "/order/list");
//
//        model.addAttribute("page", page);
//        model.addAttribute("list", responses);
//
//        return "member/list";
//    }

    /**
     * FUNCTION :: 주문 페이지
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping
    public String createForm(Pageable pageable, Model model) {
        Page<MemberDto.Response> members = memberService.findAllDesc(pageable);
        Page<ItemDto.Response> items = itemService.findAllDesc(pageable);
        model.addAttribute("members", members);
        model.addAttribute("items", items);
        return "order/orderForm";
    }

    /**
     * FUNCTION :: 상품 주문
     * @param memberId
     * @param itemId
     * @param count
     * @return
     */
    @PostMapping
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId, @RequestParam("count") int count) {
        orderService.save(memberId, itemId, count);
        return "redirect:/orders/list";
    }

    /**
     * FUNCTION :: 상품 검색
     * @param orderSearch
     * @param model
     * @return
     */
    @GetMapping(value = "/list")
    public String orderList(@ModelAttribute("orderSearch") OrderDto.OrderSearch
                                    orderSearch, Model model, Pageable pageable) {
        Page<OrderDto.Response> orders = orderService.findAllDesc(orderSearch.getOrderStatus(), orderSearch.getMemberEmail(), pageable);
        model.addAttribute("orders", orders);
        return "order/orderList";
    }

    /**
     * FUNCTION :: 상품 취소
     * @param orderId
     * @return
     */
    @PostMapping(value = "/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders/list";
    }
}
