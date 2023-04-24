package com.restaurant.repository;

import com.restaurant.entity.Reserve;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ReserveRepository {
    
    private final EntityManager em;

    //예약
    public void save(Reserve reserve) {
        em.persist(reserve);
    }
}
