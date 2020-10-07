package com.foodPro.demo.food.domain.item;


import com.foodPro.demo.food.domain.Item;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue("C")
public class Food extends Item {
    private String expirationDate; // LINE :: 유통기한

}
