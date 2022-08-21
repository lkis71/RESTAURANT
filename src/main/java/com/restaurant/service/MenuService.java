package com.restaurant.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.controller.request.MenuRequest;
import com.restaurant.entity.FileEntity;
import com.restaurant.entity.Menu;
import com.restaurant.repository.MenuRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class MenuService {
    
    private final MenuRepository menuRepository;

    @Transactional
    public void insertMenu(Menu menu) {
        menuRepository.save(menu);
    }

    public List<Menu> getMenusByRestId(Long restaurantId) {
        return menuRepository.findMenuByRestId(restaurantId);
    }

    public Menu getMenuById(Long menuId) {
        return menuRepository.findOne(menuId);
    }

    @Transactional
    public void updateMenu(Long menuId, MenuRequest menuReq) {
        Menu menu = menuRepository.findOne(menuId);
        menu.setMenu(menuReq.getMenuNm(), menuReq.getPrice(), menuReq.getSimpleContents(), menuReq.getDetailContents(), menuReq.getCategory(), menu.getRestaurant());

        if(!menuReq.getFile().isEmpty()) {
            FileEntity fileEntity = FileService.uploadFile(menuReq.getFile(), "m");
            menu.getFile().setFile(fileEntity.getFileNm(), fileEntity.getPath(), fileEntity.getSize(), fileEntity.getExtension(), fileEntity.getFileType());
            fileEntity.setFileJoinEntity(null, menu);
        }
    }

    public void deleteMenu(Long menuId) {
        menuRepository.deleteOne(menuId);
    }
}
