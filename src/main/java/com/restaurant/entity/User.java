package com.restaurant.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.restaurant.entity.common.Address;

@Entity
@Table(name = "users")
public class User {
    
    @Id @GeneratedValue
    @Column(name = "userId")
    private Long id;

    private String hmpgId;
    
    private String password;
    
    private String userName;
    
    private String phoneNum;
    
    private Address address;
    
    private String registNum;
    
    private String userType;

    public static User createUser(String hmpgId, String password, String phoneNum, Address address, String registNum, String userType) {

        User user = new User();
        user.hmpgId = hmpgId;
        user.password = password;
        user.phoneNum = phoneNum;
        user.address = address;
        user.registNum = registNum;
        user.userType = userType;

        return user;
    }
}
