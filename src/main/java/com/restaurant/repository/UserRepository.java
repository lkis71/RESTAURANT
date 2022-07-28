package com.restaurant.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.restaurant.controller.request.LoginRequest;
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

    //로그인체크
    public User ckeckUser(LoginRequest loginRequest) {
        User user = em.createQuery("select u from User u where hmpgId = :id", User.class)
            .setParameter("id", loginRequest.getHmpgId())
            .getSingleResult();

            return user;
    }
    
}
