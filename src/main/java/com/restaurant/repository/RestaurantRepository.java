package com.restaurant.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.restaurant.entity.QRestaurant;
import org.springframework.stereotype.Repository;

import com.restaurant.entity.FileEntity;
import com.restaurant.entity.Menu;
import com.restaurant.entity.Restaurant;

import lombok.RequiredArgsConstructor;

import static com.restaurant.entity.QRestaurant.restaurant;

@Repository
@RequiredArgsConstructor
public class RestaurantRepository {
    
    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

    //저장
    public void save(Restaurant restaurant) {
        em.persist(restaurant);
    }

    //단건조회
    public Restaurant findOne(Long restaurantId) {
        return jpaQueryFactory.selectFrom(restaurant)
                .leftJoin(restaurant.file)
                .where(restaurant.id.eq(restaurantId))
                .fetchJoin().fetchOne();
    }

    public List<Restaurant> findAll() {
        return em.createQuery("select r from Restaurant r"+
        " join fetch r.file f", Restaurant.class)
        .getResultList();
    }

    //페이징 조회
    public List<Restaurant> paging(int cursor, int limit) {
        return em.createQuery("select r from Restaurant r" +
        " join fetch r.file f" +
        " where r.id > :cursor" +
        " order by r.id", Restaurant.class)
            .setParameter("cursor", Long.parseLong(String.valueOf(cursor)))
            .setMaxResults(limit)
            .getResultList();
    }

    //썸네일 단건조회
    public FileEntity findFileById(Long restaurantId) {
        try{
            return em.createQuery("select f from FileEntity f" +
            " join fetch f.restaurant r" +
            " where r.id = :restaurantId", FileEntity.class)
                .setParameter("restaurantId", restaurantId)
                .getSingleResult();
        }catch (NoResultException e) {
            return null;
        }
    }

    //삭제
    public void deleteById(Long restaurantId) {
        Restaurant restaurant = findOne(restaurantId);
        em.remove(restaurant);
    }

    public List<Menu> findMenusById(Long restaurantId) {
        return em.createQuery("select m from Menu m" +
            " join fetch m.restaurant r" +
            " join fetch m.file f" +
            " where r.id = :restaurantId", Menu.class)
            .setParameter("restaurantId", restaurantId)
            .getResultList();
    }

}
