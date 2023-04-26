package com.restaurant.service;

import com.restaurant.controller.dto.MemberDto;
import com.restaurant.controller.dto.RestaurantDto;
import com.restaurant.entity.*;
import com.restaurant.entity.type.MemberType;
import com.restaurant.entity.type.RestaurantType;
import com.restaurant.entity.type.UseType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class RestaurantServiceTest {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    MemberService memberService;

    @Autowired
    FileService fileService;

    Member member;

    @BeforeEach
    public void init() {

        Long memberSeq = joinMember();
        this.member = memberService.findById(memberSeq);
    }

    @Test
    public void save_restaurant_with_file() throws Exception {
        //given
        RestaurantDto restaurantDto = createRestaurantWithFile();

        //when
        Long restaurantId = restaurantService.save(restaurantDto);

        //then
        Restaurant findRestaurant = restaurantService.findOne(restaurantId);

        assertEquals(restaurantDto.getRestaurantName(), findRestaurant.getRestaurantName());
        assertEquals(restaurantDto.getRestaurantType(), findRestaurant.getRestaurantType());
        assertEquals(restaurantDto.getFile().getOriginalFilename(), findRestaurant.getFile().getFileNm());
        assertEquals(restaurantDto.getFile().getSize(), findRestaurant.getFile().getSize());
    }

    @Test
    public void save_restaurant_except_file() throws Exception {
        //given
        RestaurantDto restaurantDto = createRestaurantExceptFile();

        //when
        Long restaurantId = restaurantService.save(restaurantDto);

        //then
        Restaurant findRestaurant = restaurantService.findOne(restaurantId);

        assertEquals(restaurantDto.getRestaurantName(), findRestaurant.getRestaurantName());
        assertEquals(restaurantDto.getRestaurantType(), findRestaurant.getRestaurantType());
    }

    @Test
    public void find_restaurant_paging() throws Exception {
        //given
        RestaurantDto restaurantDto = createRestaurantWithFile();
        int limit = 10;

        //when
        for (int i=0; i<15; i++) {
            restaurantService.save(restaurantDto);
        }

        //then
        List<Restaurant> restaurants1 = restaurantService.findByPaging(0L, limit);
        Restaurant lastRestaurant = restaurants1.get(restaurants1.size()-1);

        List<Restaurant> restaurants2 = restaurantService.findByPaging(lastRestaurant.getId(), limit);

        assertEquals(10, restaurants1.size());
        assertEquals(5, restaurants2.size());
    }

    @Test
    public void update_restaurant_with_file() throws Exception {
        //given
        RestaurantDto restaurantDto = createRestaurantWithFile();
        Long restaurantId = restaurantService.save(restaurantDto);

        //when
        RestaurantDto updateInfo = createUpdateInfoWithFile();
        restaurantService.update(restaurantId, updateInfo);

        //then
        Restaurant findRestaurant = restaurantService.findOne(restaurantId);

        assertEquals(updateInfo.getRestaurantName(), findRestaurant.getRestaurantName());
        assertEquals(updateInfo.getContact(), findRestaurant.getContact());
        assertEquals(updateInfo.getFile().getOriginalFilename(), findRestaurant.getFile().getFileNm());
        assertEquals(updateInfo.getFile().getSize(), findRestaurant.getFile().getSize());
    }

    @Test
    public void update_restaurant_except_file() throws Exception {
        //given
        RestaurantDto restaurantDto = createRestaurantWithFile();
        Long restaurantId = restaurantService.save(restaurantDto);

        //when
        RestaurantDto updateInfo = createUpdateInfoExceptFile();
        restaurantService.update(restaurantId, updateInfo);

        //then
        Restaurant findRestaurant = restaurantService.findOne(restaurantId);

        assertEquals(updateInfo.getRestaurantName(), findRestaurant.getRestaurantName());
        assertEquals(updateInfo.getContact(), findRestaurant.getContact());
    }

    @Test
    public void delete_restaurant() throws Exception {
        //given
        RestaurantDto restaurantDto = createRestaurantWithFile();

        //when
        Long restaurantId = restaurantService.save(restaurantDto);

        //then
        restaurantService.delete(restaurantId);
        Restaurant findRestaurant = restaurantService.findOne(restaurantId);

        assertEquals(UseType.REMOVE, findRestaurant.getUseType());
        assertEquals(UseType.REMOVE, findRestaurant.getFile().getUseType());
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

    private RestaurantDto createRestaurant(MockMultipartFile file) {
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
                .file(file)
                .build();
    }

    private RestaurantDto createRestaurantWithFile() throws IOException {

        MockMultipartFile file = new MockMultipartFile(
                "PIZZA",
                "다운로드 (1).png",
                "image/png",
                new FileInputStream("C:\\IdeaProject\\restaurant\\src\\main\\resources\\static\\upload\\다운로드 (1).png"));

        return createRestaurant(file);
    }

    private RestaurantDto createRestaurantExceptFile() {
        return createRestaurant(null);
    }

    private RestaurantDto createUpdateInfo(MockMultipartFile file) {
        return RestaurantDto.builder()
                .restaurantName("일본식당")
                .zipcode("222")
                .streetName("333")
                .detailAddress("444")
                .contact("010-1111-2222")
                .restaurantType(RestaurantType.JAPAN_FOOD)
                .simpleContents("간단한 내용1")
                .detailContents("상세한 내용2")
                .member(member)
                .file(file)
                .build();
    }

    private RestaurantDto createUpdateInfoWithFile() throws IOException {
        MockMultipartFile file = new MockMultipartFile(
                "CAFE",
                "다운로드 (2).png",
                "image/png",
                new FileInputStream("C:\\IdeaProject\\restaurant\\src\\main\\resources\\static\\upload\\다운로드 (2).png"));

        return createRestaurant(file);
    }

    private RestaurantDto createUpdateInfoExceptFile() {
        return createUpdateInfo(null);
    }

}