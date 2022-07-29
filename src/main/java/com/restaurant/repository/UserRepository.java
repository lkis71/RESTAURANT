package com.restaurant.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

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

    //홈페이지 아이디로 회원 조회
    public List<User> findByHmpgId(String hmpgId) {
        return em.createQuery("select u from User u where u.hmpgId = :hmpgId", User.class)
            .setParameter("hmpgId", hmpgId)
            .getResultList();
    }
}
