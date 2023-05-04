package com.restaurant.service;

import com.restaurant.controller.dto.MemberDto;
import com.restaurant.controller.dto.RestaurantDto;
import com.restaurant.entity.Member;
import com.restaurant.entity.Restaurant;
import com.restaurant.entity.RestaurantFile;
import com.restaurant.entity.type.MemberType;
import com.restaurant.entity.type.RestaurantType;
import com.restaurant.entity.type.UseType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        String memberId = joinMember();
        this.member = memberService.findById(memberId);
    }

    @Test
    public void saveRestaurantWithFile() throws Exception {
        //given
        RestaurantDto restaurantDto = createRestaurantDtoWithFile();

        //when
        Long restaurantId = restaurantService.save(restaurantDto);

        //then
        Restaurant findRestaurant = restaurantService.findOne(restaurantId);

        assertThat(restaurantId).isEqualTo(findRestaurant.getId());
        assertThat(findRestaurant.getRestaurantFiles()).isNotNull();
    }

    @Test
    public void saveRestaurantExceptFile() throws Exception {
        //given
        RestaurantDto restaurantDto = createRestaurantDto(null);

        //when
        Long restaurantId = restaurantService.save(restaurantDto);

        //then
        Restaurant findRestaurant = restaurantService.findOne(restaurantId);

        assertThat(restaurantId).isEqualTo(findRestaurant.getId());
        assertThat(findRestaurant.getRestaurantFiles()).isEmpty();
    }

    @Test
    public void findRestaurantPaging() throws Exception {
        //given
        RestaurantDto restaurantDto = createRestaurantDtoWithFile();
        int limit = 10;

        //when
        for (int i=0; i<15; i++) {
            restaurantService.save(restaurantDto);
        }

        //then
        List<RestaurantDto> restaurants1 = restaurantService.findByPaging(0L, limit);
        assertThat(10).isEqualTo(restaurants1.size());

        RestaurantDto lastRestaurant = restaurants1.get(restaurants1.size()-1);
        List<RestaurantDto> restaurants2 = restaurantService.findByPaging(lastRestaurant.getId(), limit);
        assertThat(5).isEqualTo(restaurants2.size());
    }

    @Test
    public void updateRestaurantWithFile() throws Exception {
        //given
        RestaurantDto restaurantDto = createRestaurantDtoWithFile();
        Long restaurantId = restaurantService.save(restaurantDto);

        //when
        RestaurantDto updateInfo = createUpdateInfoWithFile();
        restaurantService.update(restaurantId, updateInfo);

        //then
        Restaurant updateRestaurant = restaurantService.findOne(restaurantId);

        assertThat(updateInfo.getRestaurantName()).isEqualTo(updateRestaurant.getRestaurantName());
        assertThat(updateInfo.getRestaurantType()).isEqualTo(updateRestaurant.getRestaurantType());

        String fileName = updateInfo.getFiles().get(0).getOriginalFilename();
        String saveName = updateRestaurant.getRestaurantFiles().get(0).getFileMaster().getFileName();
        assertThat(fileName).isEqualTo(saveName);
    }

    @Test
    public void updateRestaurantExceptFile() throws Exception {
        //given
        RestaurantDto restaurantDto = createRestaurantDtoWithFile();
        Long restaurantId = restaurantService.save(restaurantDto);

        //when
        RestaurantDto updateInfo = createUpdateRestaurantDto(null);
        restaurantService.update(restaurantId, updateInfo);

        //then
        Restaurant updateRestaurant = restaurantService.findOne(restaurantId);

        assertThat(updateInfo.getRestaurantName()).isEqualTo(updateRestaurant.getRestaurantName());
        assertThat(updateInfo.getRestaurantType()).isEqualTo(updateRestaurant.getRestaurantType());
    }

    @Test
    public void deleteRestaurant() throws Exception {
        //given
        RestaurantDto restaurantDto = createRestaurantDtoWithFile();

        //when
        Long restaurantId = restaurantService.save(restaurantDto);

        //then
        restaurantService.delete(restaurantId);
        Restaurant findRestaurant = restaurantService.findOne(restaurantId);

        assertThat(UseType.REMOVE).isEqualTo(findRestaurant.getUseType());
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

    private RestaurantDto createRestaurantDto(List<MultipartFile> files) {
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

    private RestaurantDto createRestaurantDtoWithFile() throws IOException {

        List<MultipartFile> files = createFile(
                "피자집",
                "피자집.png",
                "image/png",
                "C:\\IdeaProject\\restaurant\\src\\main\\resources\\static\\upload\\피자집.png");

        return createRestaurantDto(files);
    }

    private RestaurantDto createUpdateRestaurantDto(List<MultipartFile> files) {
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

        List<MultipartFile> files = createFile(
                "레스토랑",
                "레스토랑.png",
                "image/png",
                "C:\\IdeaProject\\restaurant\\src\\main\\resources\\static\\upload\\레스토랑.png");

        return createUpdateRestaurantDto(files);
    }

    private static List<MultipartFile> createFile(String fileName, String originFileName, String contentType, String filePath) throws IOException {

        List<MultipartFile> files = new ArrayList<>();

        MultipartFile file = new MockMultipartFile(
                fileName,
                originFileName,
                contentType,
                new FileInputStream(filePath));

        files.add(file);
        return files;
    }
}