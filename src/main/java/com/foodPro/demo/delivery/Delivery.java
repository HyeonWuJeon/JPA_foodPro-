package com.foodPro.demo.delivery;

import com.foodPro.demo.config.common.Address;
import com.foodPro.demo.order.domain.Orders;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch=LAZY)
    private Orders order;

    @Embedded
    private Address address; //LINE :: 주소지

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus; //LINE :: 주문 상태 READY , COMP


    public void setOrder(Orders order){
        this.order = order;
    }

    //배송지 정보
    public void setAddress(Address address){
        this.address = address;
    }

    //주문 상태

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
}
