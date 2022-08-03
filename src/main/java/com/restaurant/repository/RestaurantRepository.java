package com.restaurant.repository;

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

    public void save(Restaurant restaurant) {
        em.persist(restaurant);
    }

    public Restaurant findOne(Long restId) {
        return em.find(Restaurant.class, restId);
    }

    public FileEntity findFileById(Long restId) {
        try{
            return em.createQuery("select f from FileEntity f join fetch f.restaurant r where r.id = :restId", FileEntity.class)
                .setParameter("restId", restId)
                .getSingleResult();

        }catch (NoResultException e) {
            return null;
        }
    }
}
