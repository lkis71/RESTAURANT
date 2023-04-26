package com.restaurant.repository;

import com.restaurant.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

}
