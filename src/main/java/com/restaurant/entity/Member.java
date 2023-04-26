package com.restaurant.entity;

import javax.persistence.*;

import com.restaurant.controller.dto.MemberDto;
import com.restaurant.entity.common.Address;

import com.restaurant.entity.type.MemberType;
import lombok.*;

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_seq")
    private Long id;

    @Column(nullable = false, unique = true)
    private String memberId;

    @Column(nullable = false)
    private String password;
    
    @Setter
    private String memberName;
    
    @Setter
    private String phoneNum;
    
    @Setter
    @Embedded
    private Address address;
    
    @Setter
    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    @Builder
    public Member(String memberId, String password, String memberName, String phoneNum, Address address, MemberType memberType) {
        this.memberId = memberId;
        this.password = password;
        this.memberName = memberName;
        this.phoneNum = phoneNum;
        this.address = address;
        this.memberType = memberType;
    }

    public void update(MemberDto memberDto) {
        this.memberName = memberDto.getMemberName();
        this.phoneNum = memberDto.getPhoneNum();
        this.memberType = memberDto.getMemberType();

        Address address = new Address(memberDto.getZipcode(), memberDto.getStreetName(), memberDto.getDetailAddress());
        this.address = address;
    }
}
