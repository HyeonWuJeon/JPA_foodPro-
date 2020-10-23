package com.foodPro.demo.order.controller;

import com.foodPro.demo.config.common.pagging.PageWrapper;
import com.foodPro.demo.member.dto.MemberDto;
import com.foodPro.demo.order.domain.OrderStatus;
import com.foodPro.demo.order.dto.OrderDto;
import com.foodPro.demo.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/list")
    public String findAllDesc(OrderStatus status, String email, Model model, Pageable pageable) {


        Page<OrderDto.Response> responses = orderService.findAllDesc(status, email, pageable);
        PageWrapper<OrderDto.Response> page = new PageWrapper<>(responses, "/order/list");

        model.addAttribute("page", page);
        model.addAttribute("list", responses);

        return "member/list";
    }

}
