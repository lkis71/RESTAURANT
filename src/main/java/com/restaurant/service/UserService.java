package com.restaurant.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.controller.request.UserRequest;
import com.restaurant.entity.User;
import com.restaurant.entity.common.Address;
import com.restaurant.repository.UserRepository;
import com.restaurant.util.CommonSession;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    //회원가입
    @Transactional
    public Long join(User user) {
        userRepository.save(user);
        if(user.getId() == null) {
            throw new IllegalArgumentException("회원가입 실패하였습니다.");
        }
        return user.getId();
    }

    //회원 시퀀스로 회원정보 조회
    public User getUserInfoBySeq(Long id) {
        return userRepository.findOne(id);
    }

    //회원수정
    @Transactional
    public void updateUserInfo(HttpServletRequest request, Long id, UserRequest userRequest) {

        Address address = new Address(userRequest.getZipcode(), userRequest.getStreetNm(), userRequest.getDetailAddress());

        User user = userRepository.findOne(id);
        user.setUserNm(userRequest.getUserNm());
        user.setRegistNum(userRequest.getRegistNum());
        user.setPhoneNum(userRequest.getPhoneNum());
        user.setAddress(address);
        user.setUserType(userRequest.getUserType());

        //세션정보 변경
        CommonSession.setSessionUserInfo(request, user);
    }
}
