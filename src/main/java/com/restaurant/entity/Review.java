package com.restaurant.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Review {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewId")
    private Long id;

    private String reviewContents;

    private Integer grade;

    @JoinColumn(name = "reserveId")
    @OneToOne(fetch = FetchType.LAZY)
    private Reserve reserve;

    @Builder
    public Review(String reviewContents, Integer grade, Reserve reserve) {
        this.reviewContents = reviewContents;
        this.grade = grade;
        this.reserve = reserve;
    }
}
