package com.restaurant.service;

import com.restaurant.controller.dto.MenuDto;
import com.restaurant.entity.FileEntity;
import com.restaurant.entity.Menu;
import com.restaurant.entity.Restaurant;
import com.restaurant.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    public void save(Long restaurantId, MenuDto menuDto) {

        Menu menu = menuDto.toEntity();

        Restaurant restaurant = restaurantService.findOne(restaurantId);
        menu.setRestaurant(restaurant);

        fileUpload(menu, menuDto.getFile());
        menuRepository.save(menu);
    }

    /**
     * 메뉴 수정
     * 
     * @param menuDto
     */
    @Transactional
    public void update(MenuDto menuDto) {

        Menu menu = menuRepository.findOne(menuDto.getId());

        fileUpload(menu, menuDto.getFile());
        menu.update(menuDto);
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
     * @param file
     */
    private void fileUpload(Menu menu, MultipartFile file) {
        
        FileEntity fileEntity = FileEntity.upload(file);
        
        if (fileEntity.isEmpty()) {
            fileService.save(fileEntity);
            menu.setFile(fileEntity);
        }
    }
}
