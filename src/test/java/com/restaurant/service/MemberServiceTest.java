package com.restaurant.service;

import com.restaurant.controller.dto.MemberDto;
import com.restaurant.entity.Member;
import com.restaurant.entity.MemberType;
import com.restaurant.entity.common.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    public void join_member() throws Exception {
        //given
        MemberDto member = setMember();

        //when
        Long memberSeq = memberService.join(member);

        //then
        Member findMember = memberService.findById(memberSeq);

        assertEquals(member.getMemberId(), findMember.getMemberId());
        assertEquals(member.getMemberName(), findMember.getMemberName());
    }

    @Test
    public void update_member() throws Exception {
        //given
        MemberDto member = setMember();

        //when
        Long memberSeq = memberService.join(member);

        Member findMember = memberService.findById(memberSeq);
        findMember.setMemberName("사용자2");
        findMember.setPhoneNum("010-1111-2222");
        findMember.setMemberType(MemberType.OWNER);

        //then
        Member updateMemeber = memberService.findById(findMember.getId());

        assertEquals(updateMemeber.getMemberName(), "사용자2");
        assertEquals(updateMemeber.getPhoneNum(), "010-1111-2222");
        assertEquals(updateMemeber.getMemberType(), MemberType.OWNER);
    }

    @Test
    public void login() throws Exception {
        //given
        MemberDto member = setMember();

        //when
        Long memberSeq = memberService.join(member);
        Member findMember1 = memberService.findById(memberSeq);

        //then
        Member findMember2 = memberService.findByMemberInfo(findMember1.getMemberId(), findMember1.getPassword());

        assertEquals(findMember1.getMemberName(), findMember2.getMemberName());
        assertEquals(findMember1.getPhoneNum(), findMember2.getPhoneNum());
        assertEquals(findMember1.getMemberType(), findMember2.getMemberType());
    }

    MemberDto setMember() {

        Address address = new Address("111", "222", "333");

        return MemberDto.builder()
                .memberName("사용자1")
                .phoneNum("010-1234-1234")
                .memberId("member1")
                .password("1234")
                .memberType(MemberType.CUSTOMER)
                .zipcode("111")
                .streetName("222")
                .detailAddress("333")
                .build();
    }
}