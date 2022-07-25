package com.restaurant.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.restaurant.entity.common.Address;

import lombok.Getter;

@Entity
@Getter
@Table(name = "users")
public class User {
    
    @Id @GeneratedValue
    @Column(name = "userId")
    private Long id;

    private String hmpgId;
    
    private String password;
    
    private String userNm;
    
    private String phoneNum;
    
    @Embedded
    private Address address;
    
    private String registNum;
    
    private String userType;

    private User(String hmpgId, String password, String userNm, String phoneNum, Address address, String registNum, String userType) {
        this.hmpgId = hmpgId;
        this.password = password;
        this.userNm = userNm;
        this.phoneNum = phoneNum;
        this.address = address;
        this.registNum = registNum;
        this.userType = userType;
    }

    // 생성메서드
    public static User createUser(String hmpgId, String password, String userNm, String phoneNum, 
                        Address address, String registNum, String userType) {

        return new User(hmpgId, password, userNm, phoneNum, address, registNum, userType);
    }
}
