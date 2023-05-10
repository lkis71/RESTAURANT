package com.restaurant.service;

import com.restaurant.controller.dto.MyRestaurantDto;
import com.restaurant.controller.dto.RestaurantDto;
import com.restaurant.controller.response.RestaurantResponse;
import com.restaurant.entity.FileMaster;
import com.restaurant.entity.Member;
import com.restaurant.entity.Restaurant;
import com.restaurant.entity.RestaurantFile;
import com.restaurant.repository.MemberRepository;
import com.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantService {

    private final FileService fileService;
    private final RestaurantRepository restaurantRepository;
    private final MemberRepository memberRepository;

    /**
     * 식당 조회(단건)
     *
     * @param restaurantId
     * @return
     */
    public Restaurant findOne(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findOne(restaurantId);
        return restaurant;
    }

    /**
     * 식당 조회(페이징)
     *
     * @param cursor 커서번호
     * @param limit 한 페이지에 보여질 목록 수
     * @return
     */
    public List<RestaurantResponse> findByPaging(Long cursor, int limit) {
        return restaurantRepository.findByPaging(cursor, limit);
    }

    /**
     * 등록 된 전체 식당 수
     *
     * @return
     */
    public int count() {
        return restaurantRepository.count();
    }

    /**
     * 식당 등록
     *
     * @param restaurantDto
     * @return
     */
    @Transactional
    public Long save(RestaurantDto restaurantDto) {

        String memberId = restaurantDto.getMemberId();
        Member findMember = memberRepository.findOne(memberId);

        Restaurant restaurant = restaurantDto.toEntity();
        restaurant.setMember(findMember);

        fileUpload(restaurant, restaurantDto.getFile());
        restaurantRepository.save(restaurant);

        return restaurant.getId();
    }

    /**
     * 식당 수정
     *
     * @param restaurantDto
     * @return
     */
    @Transactional
    public Restaurant update(RestaurantDto restaurantDto) {

        Restaurant restaurant = restaurantRepository.findOne(restaurantDto.getId());

        fileUpload(restaurant, restaurantDto.getFile());
        restaurant.update(restaurantDto);

        return restaurant;
    }

    /**
     * 식당 삭제
     *
     * @param restaurantId
     */
    @Transactional
    public void delete(Long restaurantId) {
        Restaurant findRestaurant = restaurantRepository.findOne(restaurantId);
        findRestaurant.delete();
    }

    /**
     * 파일업로드
     *
     * @param restaurant
     * @param file
     */
    private void fileUpload(Restaurant restaurant, MultipartFile file) {

        if (file == null) {
            return;
        }

        FileMaster fileMaster = FileMaster.transferTo(file);
        fileService.save(fileMaster);

        RestaurantFile restaurantFile = RestaurantFile.createRestaurantFile(restaurant, fileMaster);
        restaurantRepository.saveFile(restaurantFile);

        restaurant.setRestaurantFile(restaurantFile);
    }

    /**
     * 내 식당
     * 
     * @param memberId
     * @return
     */
    public List<MyRestaurantDto> findRestaurantByMemberId(String memberId) {
        return restaurantRepository.findRestaurantByMemberId(memberId);
    }
}
