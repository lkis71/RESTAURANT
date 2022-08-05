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
@Getter @Setter
@Table(name = "users")
@NoArgsConstructor
public class User {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long id;

    private String hmpgId;
    
    private String password;
    
    private String userNm;
    
    private String phoneNum;
    
    private Address address;
    
    private String registNum;
    
    private String userType;
    
    // 생성메서드
    public static User createUser(String hmpgId, String password, String userNm, String phoneNum, 
        String zipcode, String streetNm, String detailAddress, String registNum, String userType) {

        User user = new User();
        user.setHmpgId(hmpgId);
        user.setPassword(password);
        user.setUserNm(userNm);
        user.setPhoneNum(phoneNum);
        user.setAddress(new Address(zipcode, streetNm, detailAddress));
        user.setRegistNum(registNum);
        user.setUserType(userType);
        
        return user;
    }
}
