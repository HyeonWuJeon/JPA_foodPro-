package com.foodPro.demo.food.dto;

import com.foodPro.demo.food.domain.Item;
import com.foodPro.demo.food.domain.item.Book;
import com.foodPro.demo.food.domain.item.Clothes;
import com.foodPro.demo.food.domain.item.Food;
import lombok.*;


@Getter
@AllArgsConstructor
public class ItemDto {
    @Data
    public static class Request{

        private String gubun;
        private String name;
        private int price;
        private int stockQuantity;
        private String author;
        private String expirationDate;
        private String size;

        //테스트 코드용
//        public Request(String gubun, String name, int price, int stockQuantity, String author) {
//            this.gubun = gubun;
//            this.name = name;
//            this.price = price;
//            this.stockQuantity = stockQuantity;
//            this.author =author;
//        }

        public Book Book_toEntity() {
            return Book.builder()
                 .name(name)
                 .price(price)
                 .stockQuantity(stockQuantity)
                 .author(author)
                 .build();
        }

        public Food Food_toEntity(){
            return Food.builder()
                    .name(name)
                    .price(price)
                    .stockQuantity(stockQuantity)
                    .expirationDate(expirationDate)
                    .build();
        }

        public Clothes Clothes_toEntity(){
            return Clothes.builder()
                    .name(name)
                    .price(price)
                    .stockQuantity(stockQuantity)
                    .size(size)
                    .build();
        }
    }

    @Getter
    public static class Response{
        private String name;
        private int price;
        private int stockQuantity;

        public Response(Item entity){
            this.name = entity.getName();
            this.price =entity.getPrice();
            this.stockQuantity=entity.getStockQuantity();
        }
    }
}
