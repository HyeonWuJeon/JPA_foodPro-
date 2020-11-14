package com.foodPro.demo.order.controller;

import com.foodPro.demo.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;


    public ResponseEntity save(Long memberId, Long itemId, int count){
        orderService.save(memberId, itemId, count);
        return new ResponseEntity(HttpStatus.OK);
    }

}
