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

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class AvailableDate {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "available_id")
    private Long id;

    private LocalDateTime availableDate;

    private LocalDateTime availableTime;

    private String reserveStatus;

    @JoinColumn(name = "restaurant_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;
}
