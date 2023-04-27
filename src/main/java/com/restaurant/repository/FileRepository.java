package com.restaurant.repository;

import com.restaurant.entity.FileMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileMaster, Long> {

}
