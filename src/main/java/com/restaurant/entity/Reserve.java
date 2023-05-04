package com.restaurant.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Reserve {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserveId")
    private Long id;

    private LocalDateTime reserveDate;

    private LocalDateTime reserveTime;

    private Integer reserveUserCnt;

    private String reserveStatus;

    @JoinColumn(name = "userSeq")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member user;
    
    @JoinColumn(name = "restaurantId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    @Builder
    public Reserve(LocalDateTime reserveDate, LocalDateTime reserveTime, Integer reserveUserCnt, String reserveStatus, Member user, Restaurant restaurant) {
        this.reserveDate = reserveDate;
        this.reserveTime = reserveTime;
        this.reserveUserCnt = reserveUserCnt;
        this.reserveStatus = reserveStatus;
        this.user = user;
        this.restaurant = restaurant;
    }
}
