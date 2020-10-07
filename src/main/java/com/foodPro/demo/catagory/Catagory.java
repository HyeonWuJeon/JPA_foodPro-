package com.foodPro.demo.catagory;


import com.foodPro.demo.food.domain.CatagoryItem;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@Entity
public class Catagory {
    @Id
    @GeneratedValue
    @Column(name = "catagory_id")
    private Long id;

    private String name; //LINE :: 카테고리 이름

    // ManyToMany : 중간 매핑테이블에 필드 추가 불가능.
//    @ManyToMany //LINE :: 실무에서는 사용 금지
//    @JoinTable(name = "catagory_item",
//            joinColumns = @JoinColumn(name = "catagory_id"),
//            inverseJoinColumns = @JoinColumn(name = "item_id"))     // LINE :: 중간 테이블 매핑 . 관계형 DB는 collection 관계가 없기 때문에 1:n n:1 로 풀어낼 수 있는 DB가 필요하다
//  private List<Item> itemList = new ArrayList<>();
    @OneToMany(mappedBy = "catagory", fetch= LAZY)
    private List<CatagoryItem> catagoryItemList = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Catagory parent; // LINE :: 상위 카테고리 확인

    @OneToMany(mappedBy = "parent")
    private List<Catagory> child = new ArrayList<>(); // LINE :: 하위 코테고리 확인

    public void setParent(Catagory parent){
        this.parent = parent;
    }

    /**
     * FUNCTION :: 연관관계 메소드
     * @param child
     */
    public void addChildCatagory(Catagory child){
        this.child.add(child); //하위 카테고리 등록
        child.parent.setParent(this); //상위카테고리 등록
    }
}
