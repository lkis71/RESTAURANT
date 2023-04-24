package com.restaurant.service;

import com.restaurant.controller.dto.MemberDto;
import com.restaurant.controller.dto.RestaurantDto;
import com.restaurant.entity.Member;
import com.restaurant.entity.MemberType;
import com.restaurant.entity.Restaurant;
import com.restaurant.entity.RestaurantType;
import com.restaurant.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class RestaurantServiceTest {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    RestaurantRepository restaurantRepository;

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
        Restaurant findRestaurant = restaurantRepository.findOne(restaurantId);

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
        Restaurant findRestaurant = restaurantRepository.findOne(restaurantId);
        assertEquals(restaurantDto.getRestaurantName(), findRestaurant.getRestaurantName());
        assertEquals(restaurantDto.getRestaurantType(), findRestaurant.getRestaurantType());
    }

    private Long joinMember() {
        MemberDto memberDto = memberDto = MemberDto.builder()
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
                .restaurantName("한국식당1")
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
}