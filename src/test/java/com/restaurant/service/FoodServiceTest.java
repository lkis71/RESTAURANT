package com.restaurant.service;

import com.restaurant.controller.dto.FoodDto;
import com.restaurant.entity.type.FoodType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FoodServiceTest {

    @Autowired
    FoodService foodService;
    
    @Test
    public void savefoodWithFile() throws Exception {
        //given
        FoodDto foodDto = FoodDto.builder()
                .foodName("피자")
                .price(10000)
                .simpleContents("피자야")
                .detailContents("피자맛있어")
                .foodType(FoodType.PIZZA)
                .files()
                .build();

        //when

        //then
    }

    @Test
    public void savefoodExceptFile() throws Exception {
        //given

        //when

        //then
    }

    @Test
    public void updatefoodWithFile() throws Exception {
        //given

        //when

        //then
    }

    @Test
    public void updatefoodExceptFile() throws Exception {
        //given

        //when

        //then
    }

    @Test
    public void deletefood() throws Exception {
        //given

        //when

        //then
    }
}