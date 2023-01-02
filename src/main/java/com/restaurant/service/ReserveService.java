package com.restaurant.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.controller.request.reserveRequest;
import com.restaurant.repository.ReserveRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReserveService {
    
    private final ReserveRepository reserveRepository;

    public void reserve(reserveRequest reserveReq) {
        reserveRepository.save(reserveReq);
    }
}
