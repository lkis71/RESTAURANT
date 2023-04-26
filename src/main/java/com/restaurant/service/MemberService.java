package com.restaurant.service;

import com.restaurant.controller.dto.MemberDto;
import com.restaurant.entity.Member;
import com.restaurant.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository userRepository;

    /**
     * 회원가입
     *
     * @param memberDto
     * @return
     */
    @Transactional
    public Long join(MemberDto memberDto) {

        Member member = memberDto.toEntity();
        userRepository.save(member);

        return member.getId();
    }

    /**
     * 회원 조회(단건)
     * 
     * @param id 회원 시퀀스
     * @return
     */
    public Member findById(Long id) {
        return userRepository.findOne(id);
    }

    /**
     * 회원 조회(로그인 정보)
     * 
     * @param memberId 회원ID
     * @param password 비밀번호
     * @return
     */
    public Member findByLoginInfo(String memberId, String password) {
        return userRepository.findByLoginInfo(memberId, password);
    }

    /**
     * 회원 수정
     * 
     * @param memberDto
     * @return
     */
    @Transactional
    public Member update(MemberDto memberDto) {

        Member member = userRepository.findOne(memberDto.getId());
        member.update(memberDto);

        return member;
    }
}
