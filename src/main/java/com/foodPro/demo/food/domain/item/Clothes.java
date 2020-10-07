package com.foodPro.demo.food.domain.item;

import com.foodPro.demo.food.domain.Item;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue("S")
public class Clothes extends Item {
    private String size; // LINE :: 옷 사이즈
}
