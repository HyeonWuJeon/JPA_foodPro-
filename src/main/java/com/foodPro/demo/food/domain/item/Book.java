package com.foodPro.demo.food.domain.item;

import com.foodPro.demo.food.domain.Item;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue("P")
public class Book extends Item {
    private String author;
}
