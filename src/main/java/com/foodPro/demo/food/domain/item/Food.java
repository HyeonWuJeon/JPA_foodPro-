package com.foodPro.demo.food.domain.item;


import com.foodPro.demo.food.domain.Item;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue("F")
public class Food extends Item {
    private String expirationDate; // LINE :: 유통기한

    protected Food(){
        super();
    }

    @Builder
    public Food(String expirationDate, String name, int price, int stockQuantity) {
        super(name, price, stockQuantity);
        this.expirationDate =expirationDate;
    }
}
