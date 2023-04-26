package com.restaurant.entity.common;

import javax.persistence.Embeddable;

import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Content {
    
    private String simpleContents;
    private String detailContents;
}
