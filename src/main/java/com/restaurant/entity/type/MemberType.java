package com.restaurant.entity.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberType {

    CUSTOMER("일반회원"),
    OWNER("업주");

    private final String name;
}
