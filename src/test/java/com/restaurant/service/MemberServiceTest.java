package com.restaurant.service;

import com.restaurant.controller.dto.MemberUpdateDto;
import com.restaurant.entity.Member;
import com.restaurant.entity.MemberType;
import com.restaurant.entity.common.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService userService;

    Member member;
    
    @BeforeEach
    public void setMember() {
        
        Address address = Address.createAddress("111", "222", "333");

        this.member = Member.builder()
                .memberName("사용자1")
                .phoneNum("010-1234-1234")
                .memberId("member1")
                .password("1234")
                .memberType(MemberType.CUSTOMER)
                .address(address)
                .build();
    }

    @Test
    public void join() throws Exception {
        //given
        Long userSeq = userService.join(member);

        //when

        //then
        Member findMember = userService.findById(userSeq);

        assertEquals(member.getMemberId(), findMember.getMemberId());
        assertEquals(member.getMemberName(), findMember.getMemberName());
    }

    @Test
    public void update() throws Exception {
        //given
        Long memberSeq = userService.join(member);

        //when
        Member findMember = userService.findById(memberSeq);
        findMember.setMemberName("사용자2");
        findMember.setPhoneNum("010-1111-2222");
        findMember.setMemberType(MemberType.SHOPKEEPER);

        //then
        Member updateMemeber = userService.findById(findMember.getId());

        assertEquals(member.getMemberName(), updateMemeber.getMemberName());
        assertEquals(member.getPhoneNum(), updateMemeber.getPhoneNum());
        assertEquals(member.getMemberType(), updateMemeber.getMemberType());
    }
}