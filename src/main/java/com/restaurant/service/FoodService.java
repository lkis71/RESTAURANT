package com.restaurant.service;

import com.restaurant.controller.dto.FoodDto;
import com.restaurant.entity.*;
import com.restaurant.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodService {

    private final FoodRepository foodRepository;
    private final RestaurantService restaurantService;
    private final FileService fileService;

    /**
     * 메뉴 조회(단건)
     *
     * @param foodId
     * @return
     */
    public Food findById(Long foodId) {
        return foodRepository.findOne(foodId);
    }

    /**
     * 메뉴 조회(페이징)
     * 
     * @param restaurantId
     * @return
     */
    public List<Food> findByPaging(Long restaurantId, Long cursor, int limit) {
        return foodRepository.findByPaging(restaurantId, cursor, limit);
    }
    
    /**
     * 메뉴 등록
     * 
     * @param restaurantId
     * @param foodDto
     */
    @Transactional
    public Long save(Long restaurantId, FoodDto foodDto) {

        Food food = foodDto.toEntity();

        Restaurant restaurant = restaurantService.findOne(restaurantId);
        food.setRestaurant(restaurant);

        List<FoodFile> foodFiles = fileUpload(food, foodDto.getFiles());
        food.setFoodFiles(foodFiles);

        foodRepository.save(food);

        return food.getId();
    }

    /**
     * 메뉴 수정
     * 
     * @param foodDto
     */
    @Transactional
    public Food update(FoodDto foodDto) {

        Food food = foodRepository.findOne(foodDto.getId());

        List<FoodFile> foodFiles = fileUpload(food, foodDto.getFiles());
        food.setFoodFiles(foodFiles);

        food.update(foodDto);

        return food;
    }

    /**
     * 메뉴 삭제
     * 
     * @param foodId
     */
    @Transactional
    public void delete(Long foodId) {
        Food food = foodRepository.findOne(foodId);
        food.delete();
    }

    /**
     * 파일업로드
     *
     * @param food
     * @param files
     */
    private List<FoodFile> fileUpload(Food food, List<MultipartFile> files) {

        if (files == null) {
            return new ArrayList<>();
        }

        List<FoodFile> foodFiles = new ArrayList<>();

        for (MultipartFile file : files) {
            FileMaster fileMaster = FileMaster.transferTo(file);
            fileService.save(fileMaster);

            FoodFile foodImage = FoodFile.createFoodFile(food, fileMaster);
            foodFiles.add(foodImage);
        }

        return foodFiles;
    }
}