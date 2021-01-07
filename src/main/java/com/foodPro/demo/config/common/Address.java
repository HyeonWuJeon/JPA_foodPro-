package com.foodPro.demo.config.common;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    @Column(name = "city")
    private String city;
    @Column(name = "zipcode")
    private String zipcode;
    @Column(name = "street")
    private String street;


    /**
     * JPA 스펙상 임베디드나 엔티티의 경우 기본 생성자가 필요하다.
     * JPA구현 라이브러리가 객체를 생성할 때 프록시나 리플렉션 기술을 사용 할 수 있도록 지원해야 하기 때문이다.
     */
    public Address(String city, String street, String zipcode){
        this.city = city;
        this.street = street;
        this.zipcode =zipcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return Objects.equals(getCity(), address.getCity()) &&
                Objects.equals(getZipcode(), address.getZipcode()) &&
                Objects.equals(getStreet(), address.getStreet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getZipcode(), getStreet());
    }
}
