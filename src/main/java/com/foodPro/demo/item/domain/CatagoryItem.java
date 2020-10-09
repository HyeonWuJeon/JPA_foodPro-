package com.foodPro.demo.item.domain;


import com.foodPro.demo.catagory.Catagory;
import com.foodPro.demo.config.common.BaseTimeEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class CatagoryItem extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "order_food_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item; //LINE :: 아이템 외래키

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "catagory_id")
    private Catagory catagory; //LINE :: 카테고리 외래키


    public static CatagoryItem catalogueItem(Catagory catagory){
        CatagoryItem catalogueItem = new CatagoryItem();
        catalogueItem.setCatagory(catagory); //LINE :: 카테고리 등록

        return catalogueItem;
    }

}

