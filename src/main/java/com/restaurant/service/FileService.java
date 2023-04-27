package com.restaurant.service;

import com.restaurant.entity.FileMaster;
import com.restaurant.entity.type.UseType;
import com.restaurant.repository.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class FileService {

    private final FileRepository fileRepository;

    @Transactional
    public Long save(FileMaster fileMaster) {
        fileMaster.setUseType(UseType.USE);
        fileRepository.save(fileMaster);
        return fileMaster.getId();
    }
}
