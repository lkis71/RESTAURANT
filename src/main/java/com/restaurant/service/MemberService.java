package com.restaurant.service;

import com.restaurant.controller.dto.MemberDto;
import com.restaurant.controller.dto.MemberUpdateDto;
import com.restaurant.entity.Member;
import com.restaurant.entity.Restaurant;
import com.restaurant.entity.common.Address;
import com.restaurant.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository userRepository;

    @Transactional
    public Long join(MemberDto memberDto) {

        Address address = new Address(memberDto.getZipcode(), memberDto.getStreetName(), memberDto.getDetailAddress());

        Member member = Member.builder()
                .memberName(memberDto.getMemberName())
                .memberId(memberDto.getMemberId())
                .password(memberDto.getPassword())
                .phoneNum(memberDto.getPhoneNum())
                .memberType(memberDto.getMemberType())
                .address(address)
                .build();

        userRepository.save(member);

        return member.getId();
    }

    public Member findById(Long id) {
        return userRepository.findOne(id);
    }

    public Member findByMemberInfo(String memberId, String password) {
        return userRepository.findByMemeberInfo(memberId, password);
    }

    @Transactional
    public Member update(MemberUpdateDto memberUpdateDto) {

        Address address = new Address(
                memberUpdateDto.getZipcode(),
                memberUpdateDto.getStreetName(),
                memberUpdateDto.getDetailAddress());

        Member findMember = userRepository.findOne(memberUpdateDto.getId());
        findMember.setMemberName(findMember.getMemberName());
        findMember.setPhoneNum(findMember.getPhoneNum());
        findMember.setAddress(address);
        findMember.setMemberType(findMember.getMemberType());

        return findMember;
    }

    //내 식당 조회
    public List<Restaurant> getMyRestaurantById(Long userSeq) {
        return userRepository.findRestaurantById(userSeq);
    }
}
