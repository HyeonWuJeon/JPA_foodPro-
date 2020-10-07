package com.foodPro.demo.food.domain.item;

import com.foodPro.demo.food.domain.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue("B")
public class Book extends Item {
    private String author;

    protected Book(){
        super();
    }

    @Builder
    public Book(String author, String name, int price, int stockQuantity) {
        super(name, price, stockQuantity);
        this.author = author;
    }
}
