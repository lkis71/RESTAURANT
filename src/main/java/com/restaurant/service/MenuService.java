package com.restaurant.service;

import com.restaurant.controller.dto.MenuDto;
import com.restaurant.entity.*;
import com.restaurant.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantService restaurantService;
    private final FileService fileService;

    /**
     * 메뉴 조회(단건)
     *
     * @param menuId
     * @return
     */
    public Menu findById(Long menuId) {
        return menuRepository.findOne(menuId);
    }

    /**
     * 메뉴 조회(페이징)
     * 
     * @param restaurantId
     * @return
     */
    public List<Menu> findByRestaurantId(Long restaurantId, Long cursor, int limit) {
        return menuRepository.findByPaging(restaurantId, cursor, limit);
    }
    
    /**
     * 메뉴 등록
     * 
     * @param restaurantId
     * @param menuDto
     */
    @Transactional
    public Long save(Long restaurantId, MenuDto menuDto) {

        Menu menu = menuDto.toEntity();

        Restaurant restaurant = restaurantService.findOne(restaurantId);
        menu.setRestaurant(restaurant);

        List<MenuImage> menuImages = fileUpload(menu, menuDto.getFiles());
        menu.setMenuImages(menuImages);

        menuRepository.save(menu);

        return menu.getId();
    }

    /**
     * 메뉴 수정
     * 
     * @param menuDto
     */
    @Transactional
    public Menu update(MenuDto menuDto) {

        Menu menu = menuRepository.findOne(menuDto.getId());

        List<MenuImage> menuImages = fileUpload(menu, menuDto.getFiles());
        menu.setMenuImages(menuImages);

        menu.update(menuDto);

        return menu;
    }

    /**
     * 메뉴 삭제
     * 
     * @param menuId
     */
    @Transactional
    public void delete(Long menuId) {
        Menu menu = menuRepository.findOne(menuId);
        menu.delete();
    }

    /**
     * 파일업로드
     *
     * @param menu
     * @param files
     */
    private List<MenuImage> fileUpload(Menu menu, List<MultipartFile> files) {

        if (files == null) {
            return new ArrayList<>();
        }

        List<MenuImage> menuImages = new ArrayList<>();

        for (MultipartFile file : files) {
            FileMaster fileMaster = FileMaster.transferTo(file);
            fileService.save(fileMaster);

            MenuImage menuImage = MenuImage.createMenuImage(menu, fileMaster);
            menuImages.add(menuImage);
        }

        return menuImages;
    }
}
