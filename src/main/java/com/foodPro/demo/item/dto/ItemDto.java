package com.foodPro.demo.item.dto;

import com.foodPro.demo.item.domain.Item;
import com.foodPro.demo.item.domain.item.Book;
import com.foodPro.demo.item.domain.item.Clothes;
import com.foodPro.demo.item.domain.item.Food;
import lombok.*;


@Getter
public class ItemDto {
    @Data
    @NoArgsConstructor

    public static class Request{
        private Long id;
        private String gubun;
        private String name;
        private int price;
        private int stockQuantity;
        private String author;
        private String expirationDate;
        private String size;

        @Builder
        public Request(String gubun, String name, int price, int stockQuantity, String author, String expirationDate, String size) {
            this.gubun = gubun;
            this.name = name;
            this.price = price;
            this.stockQuantity = stockQuantity;
            this.author = author;
            this.expirationDate = expirationDate;
            this.size = size;
        }

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
        private Long id;
        private String name;
        private int price;
        private int stockQuantity;
        private String author;
        private String expirationDate;
        private String size;

        public Response(Item entity) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.price = entity.getPrice();
            this.stockQuantity = entity.getStockQuantity();
        }

        public Response(Book entity){
            this.id = entity.getId();
            this.name = entity.getName();
            this.price =entity.getPrice();
            this.stockQuantity=entity.getStockQuantity();
            this.author = entity.getAuthor();
        }
    }
}
