package com.restaurant.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FoodType {

    PIZZA("피자"),
    HAMBURGER("햄버거"),
    PASTA("파스타"),
    COFFEE("커피"),
    RICE("밥");

    private String name;
}
