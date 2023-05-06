package com.restaurant.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.restaurant.controller.dto.MyRestaurantDto;
import com.restaurant.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static com.restaurant.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 회원가입
     *
     * @param member
     */
    public void save(Member member) {
        em.persist(member);
    }

    /**
     * 회원 조회(단건)
     *
     * @param memberId 회원 아이디
     * @return
     */
    public Member findOne(String memberId) {
        return em.find(Member.class, memberId);
    }

    /**
     * 회원 조회(로그인 정보)
     *
     * @param memberId 회원ID
     * @param password 비밀번호
     * @return
     */
    public Member findByLoginInfo(String memberId, String password) {
        return jpaQueryFactory.selectFrom(member)
                .where(member.memberId.eq(memberId)
                        .and(member.password.eq(password)))
                .fetchOne();
    }
}
