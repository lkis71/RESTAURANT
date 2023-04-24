package com.restaurant.entity;

import javax.persistence.*;

import com.restaurant.entity.common.Address;

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
}
