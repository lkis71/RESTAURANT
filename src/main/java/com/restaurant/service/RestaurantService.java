package com.restaurant.service;

import com.restaurant.controller.dto.RestaurantDto;
import com.restaurant.entity.FileEntity;
import com.restaurant.entity.Restaurant;
import com.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantService {

    private final FileService fileService;
    private final RestaurantRepository restaurantRepository;

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
    public List<Restaurant> findByPaging(Long cursor, int limit) {
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

        Restaurant restaurant = restaurantDto.toEntity();

        fileUpload(restaurant, restaurantDto.getFile());
        restaurantRepository.save(restaurant);

        return restaurant.getId();
    }

    /**
     * 식당 수정
     * 
     * @param restaurantId
     * @param restaurantDto
     * @return
     */
    @Transactional
    public Restaurant update(Long restaurantId, RestaurantDto restaurantDto) {
        
        Restaurant restaurant = restaurantRepository.findOne(restaurantId);

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

        FileEntity fileEntity = FileEntity.upload(file);

        if (fileEntity.isEmpty()) {
            fileService.save(fileEntity);
            restaurant.setFile(fileEntity);
        }
    }
}
