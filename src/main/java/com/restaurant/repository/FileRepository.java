package com.restaurant.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.restaurant.entity.FileEntity;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class FileRepository {
    
    private final EntityManager em;

    public void save(FileEntity fileEntity) {
        em.persist(fileEntity);
    }
}
