package com.restaurant.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.restaurant.entity.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.restaurant.entity.QMenu.menu;
import static com.restaurant.entity.QRestaurant.restaurant;

@Repository
@RequiredArgsConstructor
public class MenuRepository {
    
    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 메뉴 등록
     *
     * @param menu
     */
    public void save(Menu menu) {
        em.persist(menu);
    }

    /**
     * 메뉴 조회(단건)
     *
     * @param menuId 메뉴아이디
     * @return
     */
    public Menu findOne(Long menuId) {
        return jpaQueryFactory.selectFrom(menu)
                .leftJoin(menu.menuImages)
                .where(menu.id.eq(menuId))
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
    public List<Menu> findByPaging(Long restaurantId, Long cursor, int limit) {
        return jpaQueryFactory.selectFrom(menu)
                .join(menu.restaurant)
                .leftJoin(menu.menuImages)
                .where(menu.restaurant.id.eq(restaurantId)
                .and(cursorId(cursor)))
                .limit(limit)
                .fetch();
    }

    private BooleanExpression cursorId(Long cursorId){
        return cursorId == null ? null : restaurant.id.gt(cursorId);
    }
}
