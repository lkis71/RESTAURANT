package com.restaurant.controller.dto;

import com.restaurant.entity.Member;
import com.restaurant.entity.common.Address;
import com.restaurant.entity.type.MemberType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberDto {
    
    private String memberId;
    private String password;
    private String memberName;
    private String phoneNum;
    private String zipcode;
    private String streetName;
    private String detailAddress;
    private MemberType memberType;

    @Builder
    public MemberDto(String memberId, String password, String memberName, String phoneNum, String zipcode, String streetName, String detailAddress, MemberType memberType) {
        this.memberId = memberId;
        this.password = password;
        this.memberName = memberName;
        this.phoneNum = phoneNum;
        this.zipcode = zipcode;
        this.streetName = streetName;
        this.detailAddress = detailAddress;
        this.memberType = memberType;
    }

    public Member toEntity() {

        Address address = new Address(zipcode, streetName, detailAddress);

        return Member.builder()
                .memberName(memberName)
                .memberId(memberId)
                .password(password)
                .phoneNum(phoneNum)
                .memberType(memberType)
                .address(address)
                .build();
    }
}
