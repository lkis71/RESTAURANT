package com.restaurant.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.restaurant.controller.request.RestaurantRequest;
import com.restaurant.entity.FileEntity;
import com.restaurant.entity.Restaurant;
import com.restaurant.entity.common.Address;
import com.restaurant.entity.common.IntroContent;
import com.restaurant.repository.RestaurantRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantService {
    
    private final RestaurantRepository restaurantRepository;
    
    //단건조회
    public Restaurant getRestaurant(Long restId) {
        Restaurant restaurant = restaurantRepository.findOne(restId);
        return restaurant;
    }

    public List<Restaurant> getRestaurants() {
        return restaurantRepository.findAll();
    }

    //페이징조회
    public List<Restaurant> getPagingRestaurant(int cursor, int limit) {
        return restaurantRepository.paging(cursor, limit);
    }

    //등록
    @Transactional
    public Long insertRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
        return restaurant.getId();
    }

    //수정
    @Transactional
    public void updateRestaurant(Long restId, RestaurantRequest restReq, MultipartFile file) {
        Restaurant restaurant = restaurantRepository.findOne(restId);
        restaurant.setRestaurantNm(restReq.getRestaurantNm());
        restaurant.setContact(restReq.getContact());
        restaurant.setCategory(restReq.getCategory());

        Address address = new Address(restReq.getZipcode(), restReq.getStreetNm(), restReq.getDetailAddress());
        restaurant.setAddress(address);

        IntroContent content = new IntroContent(restReq.getSimpleContents(), restReq.getDetailContents());
        restaurant.setContent(content);

        //파일수정
        if(!file.isEmpty()) {
            FileEntity fileInfo = FileService.uploadFile(file, "r");
            fileInfo.setFileJoinEntity(restaurant, null);
        }
    }

    //삭제
    @Transactional
    public void deleteRestaurant(Long restId) {
        restaurantRepository.deleteById(restId);
    }
}
