package com.restaurant.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public User login(HttpServletRequest request, String hmpgId) {
        User user = userRepository.findByHmpgId(hmpgId);

        if(user != null) {
            CommonSession.setSessionUserInfo(request, user);
            return user;
        }else {
            return null;
        }
    }
}
