package com.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.restaurant.service.RestaurantService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class AopTest {
    
    @Autowired
    RestaurantService restaurantService;

    @Test
    void susccess() {
        System.out.println(AopUtils.isAopProxy(restaurantService));
    }
}
