package com.restaurant.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberUpdateDto {

    private Long id;
    private String password;
    private String memberName;
    private String phoneNum;
    private String zipcode;
    private String streetName;
    private String detailAddress;

    @Builder
    public MemberUpdateDto(Long id, String password, String memberName, String phoneNum, String zipcode, String streetName, String detailAddress) {
        this.id = id;
        this.password = password;
        this.memberName = memberName;
        this.phoneNum = phoneNum;
        this.zipcode = zipcode;
        this.streetName = streetName;
        this.detailAddress = detailAddress;
    }
}
