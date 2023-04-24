package com.restaurant.service;

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

    //회원가입
    @Transactional
    public Long join(Member member) {

        userRepository.save(member);

        return member.getId();
    }

    //회원 시퀀스로 회원정보 조회
    public Member findById(Long id) {
        return userRepository.findOne(id);
    }

    //회원수정
    @Transactional
    public Member update(MemberUpdateDto userUpdateDto) {

        Address address = Address.createAddress(
                userUpdateDto.getZipcode(),
                userUpdateDto.getStreetName(),
                userUpdateDto.getDetailAddress());

        Member findMember = userRepository.findOne(userUpdateDto.getId());
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
