package com.restaurant.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.restaurant.entity.Menu;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MenuRepository {
    
    private final EntityManager em;

    public void save(Menu menu) {
        em.persist(menu);
    }

    public List<Menu> findMenuByrestaurantId(Long restaurantId) {
        return em.createQuery("select m from Menu m join fetch m.restaurant r join fetch m.file join fetch r.file where r.id = :restaurantId", Menu.class)
            .setParameter("restaurantId", restaurantId)
            .getResultList();
    }

    public Menu findOne(Long menuId) {

        try{
            return em.createQuery("select m from Menu m join fetch m.file f", Menu.class)
                .getSingleResult();
        } catch(NoResultException e) {
            return new Menu();
        } 
    }

    public void deleteOne(Long menuId) {
        Menu menu = findOne(menuId);
        em.remove(menu);
    }
}
