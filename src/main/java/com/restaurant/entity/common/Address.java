package com.restaurant.entity.common;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    
    String zipcode;
    String streetName;
    String detailAddress;
}
