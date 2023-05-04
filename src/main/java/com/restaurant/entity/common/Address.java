package com.restaurant.entity.common;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Address {
    
    private String zipcode;
    private String streetName;
    private String detailAddress;
}
