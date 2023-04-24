package com.restaurant.entity.common;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    
    private String zipcode;
    private String streetName;
    private String detailAddress;

    public static Address createAddress(String zipcode, String streetName, String detailAddress) {

        Address address = new Address();
        address.zipcode = zipcode;
        address.streetName = streetName;
        address.detailAddress = detailAddress;

        return address;
    }
}
