package com.restaurant.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.restaurant.entity.Restaurant;
import com.restaurant.entity.UseType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.restaurant.entity.QRestaurant.restaurant;

@Repository
@RequiredArgsConstructor
public class RestaurantRepository {
    
    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 식당 등록
     * 
     * @param restaurant
     */
    public void save(Restaurant restaurant) {
        em.persist(restaurant);
    }

    /**
     * 식당 조회(단건)
     * 
     * @param restaurantId 식당 시퀀스
     * @return
     */
    public Restaurant findOne(Long restaurantId) {
        return jpaQueryFactory.selectFrom(restaurant)
                .leftJoin(restaurant.file)
                .where(restaurant.id.eq(restaurantId))
                .fetchOne();
    }

    /**
     * 등록 된 전체 식당 수
     *
     * @return
     */
    public int count() {
        return jpaQueryFactory.selectFrom(restaurant)
                .fetch().size();
    }

    /**
     * 식당목록 페이징 조회
     *
     * @param cursor 커서번호
     * @param limit 한 페이지에 보여질 목록 수
     * @return
     */
    public List<Restaurant> findByPaging(Long cursor, int limit) {
        return jpaQueryFactory.selectFrom(restaurant)
                .leftJoin(restaurant.file)
                .where(cursorId(cursor)
                .and(restaurant.useType.eq(UseType.USE)))
                .orderBy(restaurant.id.asc())
                .limit(limit)
                .fetch();
    }

    private BooleanExpression cursorId(Long cursorId){
        return cursorId == null ? null : restaurant.id.gt(cursorId);
    }
}
