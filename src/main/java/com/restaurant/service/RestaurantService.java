package com.restaurant.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.restaurant.controller.dto.RestaurantDto;
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
        FileEntity file = restaurantRepository.findFileById(restId);
        restaurant.setFile(file);
        return restaurant;
    }

    //전체조회
    public List<Restaurant> getRestaurantList() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        for(Restaurant restaurant : restaurants) {
            FileEntity file = restaurantRepository.findFileById(restaurant.getId());
            restaurant.setFile(file);
        }


        return new ArrayList<>();
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
            FileEntity findFile = restaurantRepository.findFileById(restId);
            findFile.setFile(fileInfo.getFileNm(), fileInfo.getPath(), fileInfo.getSize(), fileInfo.getExtension(), fileInfo.getFileType(), restaurant, null);
        }
    }

    //삭제
    @Transactional
    public void deleteRestaurant(Long restId) {
        restaurantRepository.deleteById(restId);
    }
}
