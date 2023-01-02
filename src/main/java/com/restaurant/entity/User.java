package com.restaurant.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.restaurant.entity.common.Address;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
public class User {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long id;

    private String hmpgId;
    
    private String password;
    
    @Setter
    private String userNm;
    
    @Setter
    private String phoneNum;
    
    @Setter
    private Address address;
    
    @Setter
    private String registNum;
    
    @Setter
    private String userType;
    
    // 생성메서드
    public static User createUser(String hmpgId, String password, String userNm, String phoneNum, 
        String zipcode, String streetNm, String detailAddress, String registNum, String userType) {
        
        User user = new User();

        user.hmpgId = hmpgId;
        user.password = password;
        user.userNm = userNm;
        user.phoneNum = phoneNum;
        Address address = new Address(zipcode, streetNm, detailAddress);
        user.address = address;
        user.registNum = registNum;
        user.userType = userType;

        return user;
    }
}
