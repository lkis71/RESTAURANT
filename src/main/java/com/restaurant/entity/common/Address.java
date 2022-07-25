package com.restaurant.entity.common;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Embeddable
@Getter
@AllArgsConstructor
public class Address {
    
    private String zipcode;
    private String streetNm;
    private String detailAddress;

    protected Address() {
        
    }
}
