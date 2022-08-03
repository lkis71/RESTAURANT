package com.restaurant.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.restaurant.entity.FileEntity;
import com.restaurant.entity.Restaurant;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RestaurantRepository {
    
    private final EntityManager em;

    //저장
    public void save(Restaurant restaurant) {
        em.persist(restaurant);
    }

    //단건조회
    public Restaurant findOne(Long restId) {
        return em.find(Restaurant.class, restId);
    }

    //전체조회
    public List<Restaurant> findAll() {
        return em.createQuery("select r from Restaurant r", Restaurant.class)
            .getResultList();
    }

    //썸네일 단건조회
    public FileEntity findFileById(Long restId) {
        try{
            return em.createQuery("select f from FileEntity f join fetch f.restaurant r where r.id = :restId", FileEntity.class)
                .setParameter("restId", restId)
                .getSingleResult();
        }catch (NoResultException e) {
            return null;
        }
    }

    //삭제
    public void deleteById(Long restId) {
        Restaurant restaurant = findOne(restId);
        em.remove(restaurant);
    }

}
