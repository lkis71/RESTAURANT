package com.restaurant.controller.request;

import java.time.LocalDateTime;

import com.restaurant.entity.Reserve;

import lombok.Data;

@Data
public class reserveRequest {
    
    private LocalDateTime reserveDate;
    private LocalDateTime reserveTime;
    private Integer reserveUserCnt;

    public reserveRequest(Reserve reserve) {
        this.reserveDate = reserve.getReserveDate();
        this.reserveTime = reserve.getReserveTime();
        this.reserveUserCnt = reserve.getReserveUserCnt();
    }
}
