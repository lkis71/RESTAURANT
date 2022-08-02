package com.restaurant.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

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
}
