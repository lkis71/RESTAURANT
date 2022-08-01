package com.restaurant.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Reserve {
    
    @Id @GeneratedValue
    @Column(name = "reserveId")
    private Long id;

    private LocalDateTime reserveDate;

    private LocalDateTime reserveTime;

    private String reserveStatus;

    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    
    @JoinColumn(name = "restaurantId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;
}
