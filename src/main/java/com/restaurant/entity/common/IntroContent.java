package com.restaurant.entity.common;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Embeddable
@Getter
@AllArgsConstructor
public class IntroContent {
    
    private String simpleContents;
    private String detailContents;

    protected IntroContent() {

    }
}
