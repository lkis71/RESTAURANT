package com.restaurant.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.restaurant.entity.Restaurant;
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

    //홈페이지 아이디로 회원 조회
    public User findByLoginInfo(String hmpgId, String password) {
        try {
            return em.createQuery("select u from User u where u.hmpgId = :hmpgId and u.password = :password", User.class)
                .setParameter("hmpgId", hmpgId)
                .setParameter("password", password)
                .getSingleResult();
        } catch(NoResultException e) {
            return null;
        }
    }
    
    //회원 시퀀스로 회원 조회
    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    //내 식당 조회
    public List<Restaurant> findRestaurantById(Long userId) {
        return em.createQuery("select r from Restaurant r join fetch r.user u where u.id = :userId", Restaurant.class)
            .setParameter("userId", userId)
            .getResultList();
    }
}
