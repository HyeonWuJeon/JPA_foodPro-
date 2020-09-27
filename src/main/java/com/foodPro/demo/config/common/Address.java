package com.foodPro.demo.config.common;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor
public class Address {
    @Column(name = "city")
    private String city;
    @Column(name = "zipcode")
    private String zipcode;
    @Column(name = "street")
    private String street;

    @Builder
    public Address(String city, String zipcode, String street) {
        this.city = city;
        this.zipcode = zipcode;
        this.street = street;
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
