package com.restaurant.controller.dto;

import com.restaurant.entity.MemberType;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberDto {
    
    private Long id;
    private String memberId;
    private String password;
    private String memberName;
    private String phoneNum;
    private String zipcode;
    private String streetName;
    private String detailAddress;
    private MemberType memberType;

    @Builder
    public MemberDto(Long id, String memberId, String password, String memberName, String phoneNum, String zipcode, String streetName, String detailAddress, MemberType memberType) {
        this.id = id;
        this.memberId = memberId;
        this.password = password;
        this.memberName = memberName;
        this.phoneNum = phoneNum;
        this.zipcode = zipcode;
        this.streetName = streetName;
        this.detailAddress = detailAddress;
        this.memberType = memberType;
    }
}
