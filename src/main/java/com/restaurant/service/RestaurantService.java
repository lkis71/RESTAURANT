package com.restaurant.service;

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
    
    //식당 등록
    @Transactional
    public Long insertRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
        return restaurant.getId();
    }

    //식당 조회
    public Restaurant getRestaurantById(Long restId) {
        FileEntity file = restaurantRepository.findFileById(restId);
        Restaurant restaurant = restaurantRepository.findOne(restId);
        restaurant.setFile(file);
        return restaurant;
    }

    //식당 수정
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

        //파일 수정
        if(!file.isEmpty()) {
            FileEntity fileInfo = FileService.uploadFile(file, "r");
            FileEntity findFile = restaurantRepository.findFileById(restId);
            findFile.setFile(fileInfo, restaurant, null);
        }
    }
}
