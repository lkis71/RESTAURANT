package com.restaurant.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.restaurant.entity.User;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    // 회원 저장
    public void save(User user) {
        em.persist(user);
    }
    
}
