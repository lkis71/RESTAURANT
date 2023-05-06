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

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     *
     * @param memberDto
     * @return
     */
    @Transactional
    public String join(MemberDto memberDto) {

        Member member = memberDto.toEntity();
        memberRepository.save(member);

        return member.getMemberId();
    }

    /**
     * 회원 조회(단건)
     *
     * @param memberId 회원 아이디
     * @return
     */
    public Member findById(String memberId) {
        return memberRepository.findOne(memberId);
    }

    /**
     * 회원 조회(로그인 정보)
     *
     * @param memberId 회원ID
     * @param password 비밀번호
     * @return
     */
    public Member findByLoginInfo(String memberId, String password) {
        return memberRepository.findByLoginInfo(memberId, password);
    }

    /**
     * 회원 수정
     *
     * @param memberDto
     * @return
     */
    @Transactional
    public Member update(MemberDto memberDto) {

        Member member = memberRepository.findOne(memberDto.getMemberId());
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

        Member findMember = memberRepository.findByLoginInfo(memberDto.getMemberId(), memberDto.getPassword());

        HttpSession session = request.getSession();
        session.setAttribute("MEMBER_INFO", findMember);

        return session;
    }

    public void findRestaurantsByMemberId(String memberId) {


    }
}
