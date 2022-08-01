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
public class AvailableDate {

    @Id @GeneratedValue
    @Column(name = "availableId")
    private Long id;

    private LocalDateTime availableDate;

    private LocalDateTime availableTime;

    private String reserveStatus;

    @JoinColumn(name = "restaurantId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;
}
