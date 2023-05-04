package com.restaurant.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RestaurantType {

    KOREAN_FOOD("한식"),
    JAPAN_FOOD("일식"),
    CHINESE_FOOD("중식"),
    AMERICAN_FOOD("미식");

    private String name;
}
