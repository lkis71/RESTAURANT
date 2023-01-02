package com.restaurant.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.restaurant.controller.request.reserveRequest;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReserveRepository {
    
    private final EntityManager em;

    //예약
    public void save(reserveRequest reserveReq) {
        em.persist(reserveReq);
    }
}
