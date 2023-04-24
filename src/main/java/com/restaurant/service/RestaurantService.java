package com.restaurant.service;

import com.restaurant.controller.dto.RestaurantDto;
import com.restaurant.entity.FileEntity;
import com.restaurant.entity.Menu;
import com.restaurant.entity.Restaurant;
import com.restaurant.entity.common.Address;
import com.restaurant.entity.common.IntroContent;
import com.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantService {

    private final FileService fileService;
    private final RestaurantRepository restaurantRepository;
    
    //단건조회
    public Restaurant getRestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findOne(restaurantId);
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
    public Long save(RestaurantDto restaurantDto) {

        Address address = new Address(restaurantDto.getZipcode(), restaurantDto.getStreetName(), restaurantDto.getDetailAddress());
        IntroContent introContent = new IntroContent(restaurantDto.getSimpleContents(), restaurantDto.getDetailContents());

        Restaurant restaurant = Restaurant.builder()
                .restaurantName(restaurantDto.getRestaurantName())
                .address(address)
                .contact(restaurantDto.getContact())
                .restaurantType(restaurantDto.getRestaurantType())
                .content(introContent)
                .member(restaurantDto.getMember())
                .build();

        if (restaurantDto.getFile() != null) {
            FileEntity fileEntity = fileService.uploadFile(restaurantDto.getFile());
            fileService.save(fileEntity);

            restaurant.setFile(fileEntity);
        }

        restaurantRepository.save(restaurant);

        return restaurant.getId();
    }

    //수정
    @Transactional
    public void update(Long restaurantId, RestaurantDto restaurantDto) {
        
        Restaurant restaurant = restaurantRepository.findOne(restaurantId);
        restaurant.setRestaurant(restaurantDto.getRestaurantName(), restaurantDto.getZipcode(), restaurantDto.getStreetName(), restaurantDto.getDetailAddress(), restaurantDto.getContact(),
                restaurantDto.getRestaurantType(), restaurantDto.getSimpleContents(), restaurantDto.getDetailContents(), restaurant.getMember());

        //파일수정
        if(!restaurantDto.getFile().isEmpty()) {
            FileEntity fileEntity = fileService.uploadFile(restaurantDto.getFile());
            restaurant.getFile().setFile(fileEntity.getFileNm(), fileEntity.getPath(), fileEntity.getSize(), fileEntity.getExtension());
        }
    }

    //삭제
    @Transactional
    public void delete(Long restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }

    public List<Menu> getMenus(Long restaurantId) {
        return restaurantRepository.findMenusById(restaurantId);
    }
}
