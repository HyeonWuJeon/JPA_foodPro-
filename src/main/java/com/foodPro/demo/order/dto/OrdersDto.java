//package com.foodPro.demo.order.dto;
//
//import com.foodPro.demo.food.domain.Item;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.Getter;
//
//@Getter
//@AllArgsConstructor
//public class OrdersDto {
//    @Data
//    @AllArgsConstructor
//    @Builder
//    public static class Request{
//
//
//        public Item toEntity() {
//            return Item.builder()
//                    .name(name)
//                    .price(price)
//                    .stockQuantity(stockQuantity)
//                    .build();
//        }
//    }
//
//    @Getter
//    public static class Response{
//        private String name;
//        private int price;
//        private int stockQuantity;
//
//        public Response(Item entity){
//            this.name = entity.getName();
//            this.price =entity.getPrice();
//            this.stockQuantity=entity.getStockQuantity();
//        }
//    }
//}