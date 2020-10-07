package com.foodPro.demo.food.domain;

import com.foodPro.demo.catagory.Catagory;
import com.foodPro.demo.config.exception.NotEnoughStockException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ftype")
@Getter
@Setter
@Builder
@NoArgsConstructor
public abstract class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;
    private String name; // LINE :: 음식 이름
    private int price; // LINE :: 가격
    private int stockQuantity; // LINE :: 수량

    @ManyToMany(mappedBy = "itemList") // LINE :: 실무에서 사용 금지
    private List<Catagory> catagoryList= new ArrayList<>();


    // 비지니스 로직 :: 재고 수량 증가
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    // 비지니스 로직 :: 재고 수량 감소
    public void removeStock(int quantity){
        int restStock = this.stockQuantity  - quantity;
        if(restStock<0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}