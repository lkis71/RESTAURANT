package com.restaurant.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.restaurant.controller.dto.MyFoodDto;
import com.restaurant.controller.response.FoodResponse;
import com.restaurant.entity.Food;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static com.restaurant.entity.QFood.food;
import static com.restaurant.entity.QFoodFile.foodFile;

@Repository
@RequiredArgsConstructor
public class FoodRepository {

    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 메뉴 등록
     *
     * @param food
     */
    public void save(Food food) {
        em.persist(food);
    }

    /**
     * 메뉴 조회(단건)
     *
     * @param foodId 메뉴아이디
     * @return
     */
    public Food findOne(Long foodId) {
        return jpaQueryFactory.selectFrom(food)
                .leftJoin(food.foodFiles)
                .where(food.id.eq(foodId))
                .fetchOne();
    }

    /**
     * 메뉴 조회(페이징)
     *
     * @param restaurantId 식당아이디
     * @param cursor 커서번호
     * @param limit 한 페이지에 보여질 목록 수
     * @return
     */
    public List<FoodResponse> findByPaging(Long restaurantId, Long cursor, int limit) {

        List<Food> foods = jpaQueryFactory.selectFrom(food)
                .leftJoin(food.foodFiles, foodFile)
                .fetchJoin()
                .where(food.restaurant.id.eq(restaurantId)
                .and(cursorId(cursor)))
                .limit(limit)
                .fetch();

        return foods.stream()
                .map(o -> new FoodResponse(o))
                .collect(Collectors.toList());
    }

    private BooleanExpression cursorId(Long cursorId){
        return cursorId == null ? null : food.id.gt(cursorId);
    }
}
