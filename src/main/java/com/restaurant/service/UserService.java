package com.restaurant.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.controller.request.LoginRequest;
import com.restaurant.entity.User;
import com.restaurant.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional (readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long join(User user) {
        userRepository.save(user);
        return user.getId();
    }

    public boolean checkJoinUser(LoginRequest loginRequest) {
        List<User> user = userRepository.findByHmpgId(loginRequest.getHmpgId());

        if(user.size() == 0){
            return false;
        }else {
            if(loginRequest.getPassword().equals(user.get(0).getPassword())){
                return true;
            }else {
                return false;
            }
        }
    }
}
