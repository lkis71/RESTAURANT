package com.restaurant.service;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.restaurant.entity.FileEntity;
import com.restaurant.entity.Restaurant;
import com.restaurant.repository.FileRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class FileService {

    private final FileRepository FileRepository;
    
    public static FileEntity uploadFile(MultipartFile file, String fileType) {

        String fileNm = file.getOriginalFilename();
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

        FileEntity fileEntity = new FileEntity();
        fileEntity.setFile(fileNm, path, size, extension, fileType, new Restaurant(), null);

        return fileEntity;
    }

    @Transactional
    public Long insertFile(FileEntity fileEntity) {

        FileRepository.save(fileEntity);
        return fileEntity.getId();
    }
}
