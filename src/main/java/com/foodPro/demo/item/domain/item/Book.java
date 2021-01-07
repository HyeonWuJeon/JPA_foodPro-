package com.foodPro.demo.item.domain.item;

import com.foodPro.demo.item.domain.Item;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue("")
public class Book extends Item {
    private String author;

    public Book(){
    }

    @Builder
    public Book(String author, String name, int price, int stockQuantity) {
        super(name, price, stockQuantity);
        this.author = author;
    }

    public Book update(String author, String name, int price, int stockQuantity) {
        super.update(name, price, stockQuantity);
        this.author = author;
        return this;
    }
}
