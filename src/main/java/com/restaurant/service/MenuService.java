package com.restaurant.service;

import com.restaurant.controller.dto.MenuDto;
import com.restaurant.entity.FileEntity;
import com.restaurant.entity.Menu;
import com.restaurant.entity.Restaurant;
import com.restaurant.entity.common.IntroContent;
import com.restaurant.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantService restaurantService;
    private final FileService fileService;

    @Transactional
    public void insertMenu(Long restaurantId, MenuDto menuDto) {

        Restaurant restaurant = restaurantService.getRestaurant(restaurantId);
        IntroContent introContent = new IntroContent(menuDto.getSimpleContents(), menuDto.getDetailContents());

        Menu menu = Menu.builder()
                .menuName(menuDto.getMenuName())
                .price(menuDto.getPrice())
                .content(introContent)
                .menuType(menuDto.getMenuType())
                .restaurant(restaurant)
                .build();

        menuRepository.save(menu);

        FileEntity file = fileService.uploadFile(menuDto.getFile());
        FileEntity fileEntity = FileEntity.builder()
                .fileNm(file.getFileNm())
                .extension(file.getExtension())
                .size(file.getSize())
                .path(file.getPath())
                .build();
        fileService.save(fileEntity);
    }

    public List<Menu> getMenusByrestaurantId(Long restaurantId) {
        return menuRepository.findMenuByrestaurantId(restaurantId);
    }

    public Menu getMenuById(Long menuId) {
        return menuRepository.findOne(menuId);
    }

    @Transactional
    public void updateMenu(Long menuId, MenuDto menuDto) {

        Menu menu = menuRepository.findOne(menuId);
        menu.update(menuDto.getMenuName(), menuDto.getPrice(), menuDto.getSimpleContents(), menuDto.getDetailContents(), menuDto.getMenuType());

        if(!menuDto.getFile().isEmpty()) {
            FileEntity fileEntity = fileService.uploadFile(menuDto.getFile());
            menu.getFile().setFile(fileEntity.getFileNm(), fileEntity.getPath(), fileEntity.getSize(), fileEntity.getExtension());
        }
    }

    public void deleteMenu(Long menuId) {
        menuRepository.deleteOne(menuId);
    }
}
