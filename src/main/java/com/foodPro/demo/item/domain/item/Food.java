package com.foodPro.demo.item.domain.item;


import com.foodPro.demo.item.domain.Item;
import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter // Test
@DiscriminatorValue("")
public class Food extends Item {
    private String expirationDate; // LINE :: 유통기한


    /**
     * 테스트용
     */
    public Food(){
        super();
    }
//    protected Food(){
//        super();
//    }

    @Builder
    public Food(String expirationDate, String name, int price, int stockQuantity) {
        super(name, price, stockQuantity);
        this.expirationDate =expirationDate;
    }
}
