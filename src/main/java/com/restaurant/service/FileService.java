package com.restaurant.service;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.restaurant.entity.Menu;
import com.restaurant.entity.Restaurant;

@Service
public class FileService {
    
    public static void uploadFile(MultipartFile file, Object entity) {

        String fileNm = file.getName();
        String path = "C:\\VisualStudioCode\\Restaurant\\src\\main\\resources\\static\\upload\\";
        Long size = file.getSize();
        String extension = fileNm.substring(fileNm.indexOf(".")+1);

        File fileFolder = new File(path);

        if(!fileFolder.exists()) {
            fileFolder.mkdirs();
        }

        try {
            File uploadFile = new File(path+fileNm);
            file.transferTo(uploadFile);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }

        String fileType = "";
        if(entity.getClass().equals(Restaurant.class)) {
            fileType = "rest";
        }else if(entity.getClass().equals(Menu.class)) {
            fileType = "menu";
        }
    }
}
