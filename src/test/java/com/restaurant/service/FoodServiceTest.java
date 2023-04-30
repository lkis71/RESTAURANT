package com.restaurant.service;

import com.restaurant.controller.dto.FoodDto;
import com.restaurant.controller.dto.MemberDto;
import com.restaurant.controller.dto.RestaurantDto;
import com.restaurant.entity.Food;
import com.restaurant.entity.FoodImage;
import com.restaurant.entity.Member;
import com.restaurant.entity.type.FoodType;
import com.restaurant.entity.type.MemberType;
import com.restaurant.entity.type.RestaurantType;
import com.restaurant.entity.type.UseType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class FoodServiceTest {

    @Autowired
    FoodService foodService;

    @Autowired
    MemberService memberService;

    @Autowired
    RestaurantService restaurantService;

    Member member;

    Long restaurantId;

    @BeforeEach
    public void init() {

        Long memberSeq = joinMember();
        this.member = memberService.findById(memberSeq);

        RestaurantDto restaurantDto = createRestaurant();
        this.restaurantId = restaurantService.save(restaurantDto);
    }

    @Test
    public void saveFoodWithFile() throws Exception {

        //given
        List<MultipartFile> files = createFile();

        FoodDto foodDto = FoodDto.builder()
                .foodName("피자")
                .price(10000)
                .simpleContents("피자야")
                .detailContents("피자맛있어")
                .foodType(FoodType.PIZZA)
                .files(files)
                .build();

        //when
        Long foodId = foodService.save(restaurantId, foodDto);
        Food food = foodService.findById(foodId);

        //then
        assertThat(foodId).isEqualTo(food.getId());
    }

    @Test
    public void saveFoodExceptFile() throws Exception {

        //given
        FoodDto foodDto = FoodDto.builder()
                .foodName("피자")
                .price(10000)
                .simpleContents("피자야")
                .detailContents("피자맛있어")
                .foodType(FoodType.PIZZA)
                .files(null)
                .build();

        //when
        Long foodId = foodService.save(restaurantId, foodDto);
        Food food = foodService.findById(foodId);

        //then
        assertThat(foodId).isEqualTo(food.getId());
    }

    @Test
    public void updateFoodWithFile() throws Exception {
        //given

        //when

        //then
    }

    @Test
    public void updateFoodExceptFile() throws Exception {
        //given

        //when

        //then
    }

    @Test
    public void deleteFood() throws Exception {

        //given
        List<MultipartFile> files = createFile();

        FoodDto foodDto = FoodDto.builder()
                .foodName("피자")
                .price(10000)
                .simpleContents("피자야")
                .detailContents("피자맛있어")
                .foodType(FoodType.PIZZA)
                .files(files)
                .build();

        //when
        Long foodId = foodService.save(restaurantId, foodDto);
        foodService.delete(foodId);

        //then
        Food findFood = foodService.findById(foodId);
        assertThat(findFood.getUseType()).isEqualTo(UseType.REMOVE);

        List<FoodImage> images = findFood.getFoodImages();
        for (FoodImage image : images) {
            assertThat(image.getUseType()).isEqualTo(UseType.REMOVE);
        }
    }

    private static List<MultipartFile> createFile() throws IOException {
        List<MultipartFile> files = new ArrayList<>();

        MultipartFile file = new MockMultipartFile(
                "PIZZA",
                "다운로드 (1).png",
                "image/png",
                new FileInputStream("C:\\IdeaProject\\restaurant\\src\\main\\resources\\static\\upload\\다운로드 (1).png"));

        files.add(file);
        return files;
    }

    private Long joinMember() {
        MemberDto memberDto = MemberDto.builder()
                .memberName("사용자1")
                .phoneNum("010-1234-1234")
                .memberId("member1")
                .password("1234")
                .memberType(MemberType.CUSTOMER)
                .zipcode("111")
                .streetName("222")
                .detailAddress("333")
                .build();

        Long memberSeq = memberService.join(memberDto);
        return memberSeq;
    }

    private RestaurantDto createRestaurant() {
        return RestaurantDto.builder()
                .restaurantName("한국식당")
                .zipcode("111")
                .streetName("222")
                .detailAddress("333")
                .contact("010-1234-1234")
                .restaurantType(RestaurantType.KOREAN_FOOD)
                .simpleContents("간단한 내용")
                .detailContents("상세한 내용")
                .member(member)
                .build();
    }
}