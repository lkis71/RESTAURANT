package com.restaurant.service;

import javax.servlet.http.HttpServletRequest;

import com.restaurant.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.controller.request.LoginRequest;
import com.restaurant.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional (readOnly = true)
public class LoginService {

    private final MemberRepository userRepository;

    //로그인
    public Member login(HttpServletRequest request, LoginRequest loginRequest) {
        String memberId = loginRequest.getMemberId();
        String password = loginRequest.getPassword();
        Member user = userRepository.findByMemeberInfo(memberId, password);

        return user;
    }
}
