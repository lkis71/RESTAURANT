package com.restaurant.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.controller.request.RestaurantRequest;
import com.restaurant.entity.FileEntity;
import com.restaurant.entity.Menu;
import com.restaurant.entity.Restaurant;
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
    public void updateRestaurant(Long restId, RestaurantRequest restReq) {
        
        Restaurant restaurant = restaurantRepository.findOne(restId);
        restaurant.setRestaurant(restReq.getRestaurantNm(), restReq.getZipcode(), restReq.getStreetNm(), restReq.getDetailAddress(), restReq.getContact(), 
                restReq.getCategory(), restReq.getSimpleContents(), restReq.getDetailContents(), restaurant.getUser());

        //파일수정
        if(!restReq.getFile().isEmpty()) {
            FileEntity fileEntity = FileService.uploadFile(restReq.getFile(), "r");
            restaurant.getFile().setFile(fileEntity.getFileNm(), fileEntity.getPath(), fileEntity.getSize(), fileEntity.getExtension(), fileEntity.getFileType());
            restaurant.getFile().setFileJoinEntity(restaurant, null);
        }
    }

    //삭제
    @Transactional
    public void deleteRestaurant(Long restId) {
        restaurantRepository.deleteById(restId);
    }

    public List<Menu> getMenus(Long restId) {
        return restaurantRepository.findMenusById(restId);
    }
}
