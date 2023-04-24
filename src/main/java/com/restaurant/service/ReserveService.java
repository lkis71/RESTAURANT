package com.restaurant.service;

import com.restaurant.controller.dto.ReserveDto;
import com.restaurant.entity.Reserve;
import com.restaurant.repository.ReserveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReserveService {
    
    private final ReserveRepository reserveRepository;

    @Transactional
    public void reserve(ReserveDto reserveDto) {

        Reserve reserve = new Reserve();

        reserveRepository.save(reserve);
    }
}
