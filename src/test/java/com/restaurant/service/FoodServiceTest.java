package com.restaurant.service;

import com.restaurant.controller.dto.FoodDto;
import com.restaurant.controller.dto.MemberDto;
import com.restaurant.controller.dto.MyFoodDto;
import com.restaurant.controller.dto.RestaurantDto;
import com.restaurant.controller.response.FoodResponse;
import com.restaurant.entity.Food;
import com.restaurant.entity.FoodFile;
import com.restaurant.entity.Member;
import com.restaurant.entity.type.FoodType;
import com.restaurant.entity.type.MemberType;
import com.restaurant.entity.type.RestaurantType;
import com.restaurant.entity.type.UseType;
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

        String memberId = joinMember();
        this.member = memberService.findById(memberId);

        RestaurantDto restaurantDto = createRestaurantDto();
        this.restaurantId = restaurantService.save(restaurantDto);
    }

    @Test
    public void saveFoodWithFile() throws Exception {
        //given
        List<MultipartFile> files = createFoodFile();
        FoodDto foodDto = createFood(files);

        //when
        Long foodId = foodService.save(foodDto);

        //then
        Food food = foodService.findById(foodId);

        assertThat(foodId).isEqualTo(food.getId());
        assertThat(food.getFoodFiles()).isNotEmpty();
    }

    @Test
    public void saveFoodExceptFile() throws Exception {
        //given
        FoodDto foodDto = createFood(null);

        //when
        Long foodId = foodService.save(foodDto);
        Food food = foodService.findById(foodId);

        //then
        assertThat(food.getId()).isEqualTo(foodId);
    }

    @Test
    public void findMenuPaging() throws Exception {
        //given
        FoodDto foodDto = createFood(null);
        int limit = 10;

        //when
        for (int i=0; i<15; i++) {
            foodService.save(foodDto);
        }

        //then
        List<FoodResponse> foods1 = foodService.findByPaging(restaurantId, 0L, limit);
        assertThat(10).isEqualTo(foods1.size());

        FoodResponse lastMenu = foods1.get(foods1.size()-1);
        List<FoodResponse> foods2 = foodService.findByPaging(restaurantId, lastMenu.getId(), limit);
        assertThat(5).isEqualTo(foods2.size());
    }

    @Test
    public void updateFood() throws Exception {
        //given
        List<MultipartFile> menuFile = createFoodFile();
        FoodDto foodDto = createFood(menuFile);

        //when
        Long foodId = foodService.save(foodDto);

        foodDto.setId(foodId);
        foodDto.setFoodName("햄버거");
        foodDto.setFoodType(FoodType.HAMBURGER);
        foodService.update(foodDto);

        //then
        Food findFood = foodService.findById(foodId);

        assertThat("햄버거").isEqualTo(findFood.getFoodName());
        assertThat(FoodType.HAMBURGER).isEqualTo(findFood.getFoodType());
    }

    @Test
    public void deleteFood() throws Exception {
        //given
        List<MultipartFile> files = createFoodFile();

        FoodDto foodDto = createFood(files);

        //when
        Long foodId = foodService.save(foodDto);
        foodService.delete(foodId);

        //then
        Food findFood = foodService.findById(foodId);
        assertThat(UseType.REMOVE).isEqualTo(findFood.getUseType());

        List<FoodFile> images = findFood.getFoodFiles();
        for (FoodFile image : images) {
            assertThat(UseType.REMOVE).isEqualTo(image.getUseType());
            assertThat(UseType.REMOVE).isEqualTo(image.getFileMaster().getUseType());
        }
    }

    private FoodDto createFood(List<MultipartFile> files) {
        FoodDto foodDto = FoodDto.builder()
                .foodName("피자")
                .price(10000)
                .simpleContents("피자야")
                .detailContents("피자맛있어")
                .foodType(FoodType.PIZZA)
                .files(files)
                .restaurantId(restaurantId)
                .build();
        return foodDto;
    }

    private static List<MultipartFile> createFoodFile() throws IOException {

        List<MultipartFile> files = new ArrayList<>();

        String fileName = "불고기피자";
        String originFileName = "불고기피자.png";
        String contentType = "image/png";
        FileInputStream fis = new FileInputStream("C:\\IdeaProject\\restaurant\\src\\main\\resources\\static\\upload\\불고기피자.png");

        MultipartFile file = new MockMultipartFile(
                fileName,
                originFileName,
                contentType,
                fis);

        files.add(file);

        return files;
    }

    private String joinMember() {
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

        String memberId = memberService.join(memberDto);
        return memberId;
    }

    private RestaurantDto createRestaurantDto() {
        return RestaurantDto.builder()
                .restaurantName("한국식당")
                .zipcode("111")
                .streetName("222")
                .detailAddress("333")
                .contact("010-1234-1234")
                .restaurantType(RestaurantType.KOREAN_FOOD)
                .simpleContents("간단한 내용")
                .detailContents("상세한 내용")
                .memberId(member.getMemberId())
                .build();
    }
}