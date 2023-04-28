package com.restaurant.service;

import com.restaurant.controller.dto.MemberDto;
import com.restaurant.controller.dto.RestaurantDto;
import com.restaurant.entity.Member;
import com.restaurant.entity.Restaurant;
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

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

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

        assertThat(restaurantId).isEqualTo(findRestaurant.getId());
        assertThat(findRestaurant.getRestaurantImages()).isNotNull();
    }

    @Test
    public void save_restaurant_except_file() throws Exception {
        //given
        RestaurantDto restaurantDto = createRestaurantExceptFile();
        Restaurant actual = restaurantDto.toEntity();

        //when
        Long restaurantId = restaurantService.save(restaurantDto);

        //then
        Restaurant findRestaurant = restaurantService.findOne(restaurantId);

        assertThat(restaurantId).isEqualTo(findRestaurant.getId());
        assertThat(findRestaurant.getRestaurantImages()).isEmpty();
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

        assertThat(10).isEqualTo(restaurants1.size());
        assertThat(5).isEqualTo(restaurants2.size());
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
        Restaurant updateRestaurant = restaurantService.findOne(restaurantId);

        assertThat(updateInfo.getRestaurantName()).isEqualTo(updateRestaurant.getRestaurantName());
        assertThat(updateInfo.getRestaurantType()).isEqualTo(updateRestaurant.getRestaurantType());
        assertThat(updateInfo.getFiles().get(0).getOriginalFilename()).isEqualTo(updateRestaurant.getRestaurantImages().get(0).getFileMaster().getFileNm());
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
        Restaurant updateRestaurant = restaurantService.findOne(restaurantId);

        assertThat(updateInfo.getRestaurantName()).isEqualTo(updateRestaurant.getRestaurantName());
        assertThat(updateInfo.getRestaurantType()).isEqualTo(updateRestaurant.getRestaurantType());
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

        assertThat(UseType.REMOVE).isEqualTo(findRestaurant.getUseType());
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

    private RestaurantDto createRestaurant(List<MultipartFile> files) {
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
                .files(files)
                .build();
    }

    private RestaurantDto createRestaurantWithFile() throws IOException {

        List<MultipartFile> files = new ArrayList<>();

        MultipartFile file = new MockMultipartFile(
                "PIZZA",
                "다운로드 (1).png",
                "image/png",
                new FileInputStream("C:\\IdeaProject\\restaurant\\src\\main\\resources\\static\\upload\\다운로드 (1).png"));

        files.add(file);

        return createRestaurant(files);
    }

    private RestaurantDto createRestaurantExceptFile() {
        return createRestaurant(null);
    }

    private RestaurantDto createUpdateInfo(List<MultipartFile> files) {
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
                .files(files)
                .build();
    }

    private RestaurantDto createUpdateInfoWithFile() throws IOException {

        List<MultipartFile> files = new ArrayList<>();

        MultipartFile file = new MockMultipartFile(
                "HAMBURGER",
                "다운로드 (2).png",
                "image/png",
                new FileInputStream("C:\\IdeaProject\\restaurant\\src\\main\\resources\\static\\upload\\다운로드 (2).png"));

        files.add(file);

        return createUpdateInfo(files);
    }

    private RestaurantDto createUpdateInfoExceptFile() {
        return createUpdateInfo(null);
    }

}