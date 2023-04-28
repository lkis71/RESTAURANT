package com.restaurant.service;

import com.restaurant.controller.dto.MemberDto;
import com.restaurant.entity.Member;
import com.restaurant.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRespository;

    /**
     * 회원가입
     *
     * @param memberDto
     * @return
     */
    @Transactional
    public Long join(MemberDto memberDto) {

        Member member = memberDto.toEntity();
        memberRespository.save(member);

        return member.getId();
    }

    /**
     * 회원 조회(단건)
     * 
     * @param id 회원 시퀀스
     * @return
     */
    public Member findById(Long id) {
        return memberRespository.findOne(id);
    }

    /**
     * 회원 조회(로그인 정보)
     * 
     * @param memberId 회원ID
     * @param password 비밀번호
     * @return
     */
    public Member findByLoginInfo(String memberId, String password) {
        return memberRespository.findByLoginInfo(memberId, password);
    }

    /**
     * 회원 수정
     * 
     * @param memberDto
     * @return
     */
    @Transactional
    public Member update(MemberDto memberDto) {

        Member member = memberRespository.findOne(memberDto.getId());
        member.update(memberDto);

        return member;
    }

    /**
     * 로그인
     *
     * @param request
     * @param memberDto
     */
    public HttpSession login(HttpServletRequest request, MemberDto memberDto) {

        Member findMember = memberRespository.findByLoginInfo(memberDto.getMemberId(), memberDto.getPassword());

        HttpSession session = request.getSession();
        session.setAttribute("MEMBER_INFO", findMember);

        return session;
    }
}
