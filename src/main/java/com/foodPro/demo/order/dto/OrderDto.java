package com.foodPro.demo.order.dto;

import com.foodPro.demo.delivery.Delivery;
import com.foodPro.demo.member.domain.Member;
import com.foodPro.demo.order.domain.Order;
import com.foodPro.demo.order.domain.OrderItem;
import com.foodPro.demo.order.domain.OrderStatus;
import lombok.*;

import java.util.List;

@Getter
public class OrderDto {

    @Data
    public static class OrderSearch{
        private String memberEmail;
        private OrderStatus orderStatus;
    }
    @Getter
    public static class Response{
        private Long id;
        private Member member;
        private List<OrderItem> orderItemList;
        private Delivery delivery;
        private OrderStatus orderStatus;

        public Response(Order entity){
            this.id = entity.getId();
            this.member = entity.getMember();
            this.delivery = entity.getDelivery();
            this.orderItemList = entity.getOrderItemList();
            this.orderStatus = entity.getOrderStatus();
        }
    }
}