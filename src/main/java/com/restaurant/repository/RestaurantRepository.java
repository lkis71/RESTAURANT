package com.restaurant.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
                .fetchOne();
    }

    public int count() {
        return jpaQueryFactory.selectFrom(restaurant)
                .fetch().size();
    }

    //페이징 조회
    public List<Restaurant> findByPaging(Long cursor, int limit) {
        return jpaQueryFactory.selectFrom(restaurant)
                .leftJoin(restaurant.file)
                .where(cursorId(cursor))
                .orderBy(restaurant.id.asc())
                .limit(limit)
                .fetch();
    }

    private BooleanExpression cursorId(Long cursorId){
        return cursorId == null ? null : restaurant.id.gt(cursorId);
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

    public List<Menu> findMenusById(Long restaurantId) {
        return em.createQuery("select m from Menu m" +
            " join fetch m.restaurant r" +
            " join fetch m.file f" +
            " where r.id = :restaurantId", Menu.class)
            .setParameter("restaurantId", restaurantId)
            .getResultList();
    }

}
