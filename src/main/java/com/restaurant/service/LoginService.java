package com.restaurant.service;

import com.restaurant.controller.dto.LoginDto;
import com.restaurant.entity.Member;
import com.restaurant.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional (readOnly = true)
public class LoginService {

    private final MemberRepository memberRespository;

    //로그인
    public Member login(LoginDto loginDto) {
        return memberRespository.findByLoginInfo(loginDto.getMemberId(), loginDto.getPassword());
    }
}
