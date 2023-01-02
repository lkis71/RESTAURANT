package com.restaurant.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.controller.request.LoginRequest;
import com.restaurant.entity.User;
import com.restaurant.repository.UserRepository;
import com.restaurant.util.CommonSession;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional (readOnly = true)
public class LoginService {

    private final UserRepository userRepository;

    //로그인
    public User login(HttpServletRequest request, LoginRequest loginRequest) {
        String hmpgId = loginRequest.getHmpgId();
        String password = loginRequest.getPassword();
        User user = userRepository.findByLoginInfo(hmpgId, password);

        return user;
    }
}
