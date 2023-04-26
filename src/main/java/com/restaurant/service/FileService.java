package com.restaurant.service;

import com.restaurant.entity.FileEntity;
import com.restaurant.entity.Restaurant;
import com.restaurant.repository.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class FileService {

    private final FileRepository FileRepository;

    @Transactional
    public Long save(FileEntity fileEntity) {
        FileRepository.save(fileEntity);
        return fileEntity.getId();
    }
}
