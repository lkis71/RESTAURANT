package com.restaurant.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.restaurant.entity.Member;
import com.restaurant.entity.Restaurant;
import com.restaurant.exception.AlreadyExistMemberIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.restaurant.entity.QMember.member;
import static com.restaurant.entity.QRestaurant.restaurant;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

    // 회원 저장
    public void save(Member member) {
        em.persist(member);
    }

    //회원 시퀀스로 회원 조회
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }


    /**
     * findMember
     *
     * @param memberId 사용자ID
     * @param password 패스워드
     * @return
     */
    public Member findByMemeberInfo(String memberId, String password) {
        return jpaQueryFactory.selectFrom(member)
                .where(member.memberId.eq(memberId)
                .and(member.password.eq(password)))
                .fetchOne();
    }


    //내 식당 조회
    public List<Restaurant> findRestaurantById(Long userSeq) {
        return jpaQueryFactory.selectFrom(restaurant)
                .join(restaurant.user, member)
                .fetch();
    }
}
